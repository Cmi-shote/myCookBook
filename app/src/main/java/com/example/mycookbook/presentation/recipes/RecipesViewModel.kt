package com.example.mycookbook.presentation.recipes

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycookbook.data.DataStoreRepository
import com.example.mycookbook.data.MealAndDietType
import com.example.mycookbook.data.database.Repository
import com.example.mycookbook.data.database.entities.FavoritesEntity
import com.example.mycookbook.data.database.entities.GroceryEntity
import com.example.mycookbook.data.database.entities.RecipesEntity
import com.example.mycookbook.data.model.FoodRecipe
import com.example.mycookbook.data.model.Grocery
import com.example.mycookbook.presentation.utils.handleFoodRecipesResponse
import com.example.mycookbook.presentation.utils.hasInternetConnection
import com.example.mycookbook.util.Constants.Companion.API_KEY
import com.example.mycookbook.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.mycookbook.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.mycookbook.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.mycookbook.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.mycookbook.util.Constants.Companion.QUERY_API_KEY
import com.example.mycookbook.util.Constants.Companion.QUERY_DIET
import com.example.mycookbook.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.mycookbook.util.Constants.Companion.QUERY_NUMBER
import com.example.mycookbook.util.Constants.Companion.QUERY_SEARCH
import com.example.mycookbook.util.Constants.Companion.QUERY_TYPE
import com.example.mycookbook.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesViewModel(
    private val context: Context,
    private val dataStoreRepository: DataStoreRepository,
    private val repository: Repository
): ViewModel() {
    private lateinit var mealAndDiet: MealAndDietType

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    private val _backOnlineState = MutableStateFlow(false)
    val backOnlineState: StateFlow<Boolean> = _backOnlineState.asStateFlow()

    private val _recipes = MutableStateFlow<NetworkResult<FoodRecipe>>(NetworkResult.Loading())
    val recipes: StateFlow<NetworkResult<FoodRecipe>> = _recipes.asStateFlow()

    private val _favoriteRecipes = MutableStateFlow<List<FavoritesEntity>>(emptyList())
    val favoriteRecipes: StateFlow<List<FavoritesEntity>> = _favoriteRecipes.asStateFlow()

    private val _searchedRecipes = MutableStateFlow<NetworkResult<FoodRecipe>>(NetworkResult.Loading())
    val searchedRecipes: StateFlow<NetworkResult<FoodRecipe>> = _searchedRecipes.asStateFlow()

    private val _popularRecipes = MutableStateFlow(FoodRecipe(emptyList()))
    val popularRecipes: StateFlow<FoodRecipe> = _popularRecipes.asStateFlow()

    private val _groceryList = MutableStateFlow<List<GroceryEntity>>(emptyList())
    val groceryList: StateFlow<List<GroceryEntity>> = _groceryList.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.readBackOnline.collect { backOnlineValue ->
                _backOnlineState.value = backOnlineValue
            }
        }

        // Observe favorite recipes
        viewModelScope.launch {
            repository.local.readFavoriteRecipes().collect { favorites ->
                _favoriteRecipes.value = favorites
            }
        }

        // observe grocery list
        viewModelScope.launch {
            repository.local.readGroceryList().collect { groceryList ->
                _groceryList.value = groceryList
            }
        }

        getRecipes(applyQueries())
    }

    fun saveMealAndDietType() =
        viewModelScope.launch {
            if (this@RecipesViewModel::mealAndDiet.isInitialized) {
                dataStoreRepository.saveMealAndDietType(
                    mealAndDiet.selectedMealType,
                    mealAndDiet.selectedMealTypeId,
                    mealAndDiet.selectedDietType,
                    mealAndDiet.selectedDietTypeId
                )
            }
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        if (this@RecipesViewModel::mealAndDiet.isInitialized) {
            queries[QUERY_TYPE] = mealAndDiet.selectedMealType
            queries[QUERY_DIET] = mealAndDiet.selectedDietType
        } else {
            queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
            queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        }

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(context, "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipes.value = NetworkResult.Loading()
        if (hasInternetConnection(context)) {
            try {
                val response = repository.remote.getRecipes(queries)
                val result = handleFoodRecipesResponse(response)
                _recipes.value = result
                val popularRecipesList = result.data?.results?.filter { recipe ->
                    recipe.aggregateLikes > 1000
                } ?: emptyList()

                _popularRecipes.value = FoodRecipe(popularRecipesList)

                // Cache successful responses
                result.data?.let { foodRecipe ->
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                _recipes.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _recipes.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _searchedRecipes.value = NetworkResult.Loading()
        if (hasInternetConnection(context)) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                _searchedRecipes.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                _searchedRecipes.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _searchedRecipes.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }

    fun insertGroceryItem(groceryEntity: GroceryEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertGroceryList(groceryEntity)
        }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteGroceryItem(groceryEntity: GroceryEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteGroceryItem(groceryEntity)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Item removed from grocery list", Toast.LENGTH_SHORT).show()
            }
        }
}