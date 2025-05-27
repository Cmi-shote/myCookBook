package com.example.mycookbook.di

import android.app.Application
import androidx.room.Room
import com.example.mycookbook.data.database.CookBookDatabase

fun provideDataBase(application: Application) : CookBookDatabase =
    Room.databaseBuilder(
        application,
        CookBookDatabase::class.java,
        "cookBook_db"
    ).build()

fun provideDao(cookBookDataBase: CookBookDatabase) = cookBookDataBase.recipesDao()
