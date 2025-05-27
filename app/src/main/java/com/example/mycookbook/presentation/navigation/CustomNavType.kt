package com.example.mycookbook.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class CustomNavType<T>(
    private val serializer: KSerializer<T>
) : NavType<T>(false) {
    override fun get(bundle: Bundle, key: String): T? {
        return Json.decodeFromString(serializer, bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): T {
        val decoded = Uri.decode(value)
        return Json.decodeFromString(serializer, decoded)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }

    override fun serializeAsValue(value: T): String {
        val encoded = Json.encodeToString(serializer, value)
        return Uri.encode(encoded)
    }
}