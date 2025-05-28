package com.example.mycookbook.di

import com.example.mycookbook.data.DataStoreRepository
import com.example.mycookbook.data.LocalDataSource
import com.example.mycookbook.data.database.RemoteDataSource
import com.example.mycookbook.data.database.Repository
import com.example.mycookbook.presentation.AppViewModel
import com.example.mycookbook.presentation.recipes.RecipesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }
    viewModel { RecipesViewModel(androidContext(), get(), get()) }
    single { DataStoreRepository(androidContext()) }
    single { LocalDataSource(get()) }
    single { Repository(get(), get()) }
}