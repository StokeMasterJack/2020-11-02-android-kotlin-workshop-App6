@file:Suppress("PrivatePropertyName")

package akw.app6

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val INTUIT_PREFERENCES_NAME = "intuit_preferences"


data class Prefs(
    val showScreen1: Boolean = true,
    val showScreen2: Boolean = true,
)

class PrefsRepo(context: Context) {

    private object PrefsKeys {
        val SHOW_SCREEN_1 = preferencesKey<Boolean>("show_screen_1")
        val SHOW_SCREEN_2 = preferencesKey<Boolean>("show_screen_2")
    }


    private val TAG: String = "PrefRepo"

    val dataStore: DataStore<Preferences> = context.createDataStore(name = INTUIT_PREFERENCES_NAME)

    val prefsFlow: Flow<Prefs> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val showScreen1: Boolean = preferences[PrefsKeys.SHOW_SCREEN_1] ?: true
            val showScreen2: Boolean = preferences[PrefsKeys.SHOW_SCREEN_2] ?: true
            Prefs(showScreen1, showScreen2)
        }

    suspend fun updateShowScreen1(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SHOW_SCREEN_1] = newValue
        }
    }

    suspend fun updateShowScreen2(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SHOW_SCREEN_2] = newValue
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PrefsRepo? = null

        fun getInstance(context: Context): PrefsRepo {
            return INSTANCE ?: synchronized(this) {
                val instance = PrefsRepo(context)
                INSTANCE = instance
                instance
            }
        }
    }
}