package com.channey.utils

import android.app.Activity
import android.content.Context
import java.util.*
import kotlin.collections.HashSet

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
    fun saveBoolean(context:Context,key: String, value: Boolean,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * Retrieve a boolean value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getBoolean(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE): Boolean? {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getBoolean(key, false)
    }

    /**
     * Set a String value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveString(context:Context,key: String, value: String,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Set a String set value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveStringSet(context:Context,key: String, value: Set<String>,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putStringSet(key, value)
        editor.commit()
    }

    /**
     * Retrieve a String value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getString(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE): String {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getString(key, "")
    }

    fun getStringSet(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE): Set<String> {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getStringSet(key, HashSet<String>())
    }

    /**
     * Mark in the editor that a preference value should be removed
     * @param context
     * @param key The name of the preference to modify.
     */
    fun remove(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
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
    fun saveLong(context: Context, key: String, value: Long,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Retrieve a long value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getLong(context: Context, key: String,mode:Int = Activity.MODE_PRIVATE): Long {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getLong(key, 0)
    }
}