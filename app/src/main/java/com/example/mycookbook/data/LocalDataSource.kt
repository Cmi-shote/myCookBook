package com.example.mycookbook.data

import com.example.mycookbook.data.database.RecipesDao
import com.example.mycookbook.data.database.entities.FavoritesEntity
import com.example.mycookbook.data.database.entities.GroceryEntity
import com.example.mycookbook.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource (
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    fun readGroceryList(): Flow<List<GroceryEntity>> {
        return recipesDao.readGroceryList()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun insertGroceryList(groceryEntity: GroceryEntity) {
        recipesDao.insertGroceryList(groceryEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}