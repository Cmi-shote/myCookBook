package com.example.mycookbook.di

import org.koin.dsl.module

val databaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}