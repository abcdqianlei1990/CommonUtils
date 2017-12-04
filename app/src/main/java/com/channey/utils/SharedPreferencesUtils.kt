package com.channey.utils

import android.app.Activity
import android.content.Context

/**
 * Created by channey on 2017/8/2.
 */
object SharedPreferencesUtils {
    private val FILE_NAME = "channey.sp"

    /**
     * Set a boolean value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveBoolean(context:Context,key: String, value: Boolean) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * Retrieve a boolean value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getBoolean(context:Context,key: String): Boolean? {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        return sp.getBoolean(key, false)
    }

    /**
     * Set a String value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveString(context:Context,key: String, value: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Retrieve a String value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getString(context:Context,key: String): String {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        return sp.getString(key, "")
    }

    /**
     * Mark in the editor that a preference value should be removed
     * @param context
     * @param key The name of the preference to modify.
     */
    fun remove(context:Context,key: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.commit()
    }

    /**
     * Set a long value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveLong(context: Context, key: String, value: Long) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Retrieve a long value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getLong(context: Context, key: String): Long {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        return sp.getLong(key, 0)
    }
}