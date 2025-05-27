package com.example.mycookbook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycookbook.data.model.FoodRecipe
import com.example.mycookbook.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}