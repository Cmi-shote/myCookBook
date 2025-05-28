package com.example.mycookbook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var result: RecipeDetails
)