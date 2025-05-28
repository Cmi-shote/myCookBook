package com.example.mycookbook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mycookbook.data.database.GroceryTypeConverters
import com.example.mycookbook.data.model.Grocery
import com.example.mycookbook.util.Constants.Companion.GROCERY_TABLE

@Entity(tableName = GROCERY_TABLE)
@TypeConverters(GroceryTypeConverters::class)
class GroceryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var result: Grocery
)