package com.example.mycookbook.di

import com.example.mycookbook.data.database.RemoteDataSource
import com.example.mycookbook.data.database.network.FoodRecipesApi
import com.example.mycookbook.util.Constants.Companion.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Use your base URL here
            .addConverterFactory(GsonConverterFactory.create()) // or Kotlinx Serialization converter
            .build()
    }

    single<FoodRecipesApi> {
        get<Retrofit>().create(FoodRecipesApi::class.java)
    }

    single {
        RemoteDataSource(get())
    }
}