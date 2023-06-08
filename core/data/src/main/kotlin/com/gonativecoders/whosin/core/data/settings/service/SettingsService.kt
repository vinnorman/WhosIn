package com.gonativecoders.whosin.core.data.settings.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

internal class SettingsService(private val context: Context) {

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


    companion object {


        const val HAS_COMPLETED_TUTORIAL = "has_completed_tutorial"
    }


}