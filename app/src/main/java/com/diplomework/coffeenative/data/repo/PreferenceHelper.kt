package com.diplomework.coffeenative.data.repo

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object PreferenceHelper {
    lateinit var prefs: SharedPreferences
    private const val PREFERENCES_FILE_NAME = "ABSOLUTELY_ANY_STRING"

    /**
     * this method should be called when app starts, or when we trying to get shared prefs first time.
     *
     * @param context reference of context, which has possibility to access shared prefs.
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> save(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        prefs.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> load(key: String): T? {
        val value = prefs.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }
}