package com.yuvrajsinghgmx.shopsmart.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.yuvrajsinghgmx.shopsmart.screens.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.contracts.contract
import kotlin.text.get

data class Poduct(val name: String, val amount: Int, val imageUrl: String? = null)

object ShoppingList{
    val ITEMS_KEY = stringPreferencesKey("items")
}

suspend fun saveItems(context: Context, items: List<Poduct>) {
    context.dataStore.edit { preferences ->
        val jsonString = Gson().toJson(items)
        preferences[ShoppingList.ITEMS_KEY] = jsonString
    }
}

fun getItems(context: Context): Flow<List<Product>> = context.dataStore.data
    .map { preferences ->
        val json = preferences[ShoppingList.ITEMS_KEY] ?: return@map emptyList()
        Gson().fromJson(json, Array<Product>::class.java).toList()
    }