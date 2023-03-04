package com.gonativecoders.whosin.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreRepository(private val context: Context) {

    suspend fun putBoolean(key: String, value: Boolean) {
        val booleanKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[booleanKey] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean {
        val booleanKey = booleanPreferencesKey(key)
        return context.dataStore.data.first()[booleanKey] ?: false
    }

    suspend fun putString(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[stringKey] = value
        }
    }

    suspend fun getString(key: String): String? {
        val stringKey = stringPreferencesKey(key)
        context.dataStore.data.first()
        return context.dataStore.data.first()[stringKey]
    }

    companion object {

        const val TEAM_ID = "teamId"
    }

}