package com.smartflowtech.cupidcustomerapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStorePrefsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    data class AppConfigPreferences(
        val onBoarded: Boolean = false,
        val loggedIn: Boolean = false,
        val userName: String = "",
        val userEmail: String = "",
        val token: String = "",
        val phoneNumber: String = ""
    )

    private object PreferencesKeys {
        val ONBOARDED = booleanPreferencesKey("onboarded")
        val LOGGED_IN = booleanPreferencesKey("loggedIn")
        val USERNAME = stringPreferencesKey("userName")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val TOKEN = stringPreferencesKey("token")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
    }

    val appConfigPreferencesAsFlow: Flow<AppConfigPreferences> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        mapUserPreferences(preferences)
    }

    suspend fun getAppConfigPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): AppConfigPreferences {
        return AppConfigPreferences(
            onBoarded = preferences[PreferencesKeys.ONBOARDED] ?: false,
            loggedIn = preferences[PreferencesKeys.LOGGED_IN] ?: false,
            userName = preferences[PreferencesKeys.USERNAME] ?: "",
            userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "",
            token = preferences[PreferencesKeys.TOKEN] ?: "",
            phoneNumber = preferences[PreferencesKeys.PHONE_NUMBER] ?: ""
        )
    }

    suspend fun updateStarted(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.ONBOARDED] = bool
        }
    }

    suspend fun updateLoggedIn(
        loggedIn: Boolean,
        username: String,
        userEmail: String,
        token: String,
        phoneNumber: String
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.LOGGED_IN] = loggedIn
            mutablePreferences[PreferencesKeys.USERNAME] = username
            mutablePreferences[PreferencesKeys.USER_EMAIL] = userEmail
            mutablePreferences[PreferencesKeys.TOKEN] = token
            mutablePreferences[PreferencesKeys.PHONE_NUMBER] = phoneNumber
        }
    }


}