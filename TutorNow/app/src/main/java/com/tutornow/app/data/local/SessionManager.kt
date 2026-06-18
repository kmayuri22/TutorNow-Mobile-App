package com.tutornow.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tutornow_session")

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_ROLE = stringPreferencesKey("user_role")
        private val KEY_NAME = stringPreferencesKey("user_name")
        private val KEY_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_ID = intPreferencesKey("user_id")
    }

    fun saveSession(token: String, role: String, name: String, email: String, userId: Int) {
        runBlocking {
            context.dataStore.edit { prefs ->
                prefs[KEY_TOKEN] = token
                prefs[KEY_ROLE] = role
                prefs[KEY_NAME] = name
                prefs[KEY_EMAIL] = email
                prefs[KEY_USER_ID] = userId
            }
        }
    }

    fun getToken(): String? = runBlocking {
        context.dataStore.data.first()[KEY_TOKEN]
    }

    fun getRole(): String? = runBlocking {
        context.dataStore.data.first()[KEY_ROLE]
    }

    fun getName(): String? = runBlocking {
        context.dataStore.data.first()[KEY_NAME]
    }

    fun getEmail(): String? = runBlocking {
        context.dataStore.data.first()[KEY_EMAIL]
    }

    fun getUserId(): Int = runBlocking {
        context.dataStore.data.first()[KEY_USER_ID] ?: -1
    }

    fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()

    fun clearSession() {
        runBlocking {
            context.dataStore.edit { it.clear() }
        }
    }
}
