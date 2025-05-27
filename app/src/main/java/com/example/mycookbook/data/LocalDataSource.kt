package com.example.mycookbook.data

import com.example.mycookbook.data.database.RecipesDao
import com.example.mycookbook.data.database.entities.FavoritesEntity
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

//    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
//        return recipesDao.readFoodJoke()
//    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

//    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
//        recipesDao.insertFoodJoke(foodJokeEntity)
//    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}