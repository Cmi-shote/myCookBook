package com.example.mycookbook.presentation.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"

// Extension property on Context
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferences(private val context: Context) {

    companion object {
        val ONBOARDING_KEY = booleanPreferencesKey("onboarding_seen")
    }

    val onboardingSeen: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_KEY] ?: false
        }

    suspend fun setOnboardingSeen(seen: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = seen
        }
    }
}