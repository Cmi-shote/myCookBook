package com.example.mycookbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycookbook.data.database.entities.FavoritesEntity
import com.example.mycookbook.data.database.entities.GroceryEntity
import com.example.mycookbook.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, GroceryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CookBookDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}