package com.channey.utils

import android.app.Activity
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList
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
    fun getBoolean(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE): Boolean? {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getBoolean(key, false)
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
    fun saveString(context:Context,key: String, value: String,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.commit()
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
     * Set a String set value in the preferences editor
     * @param context
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun saveStringSet(context:Context,key: String, value: Set<String>) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
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

    /**
     * Retrieve a String value from the preferences.
     * @param context
     * @param key The name of the preference to modify.
     */
    fun getString(context:Context,key: String): String {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        return sp.getString(key, "")
    }

    fun getStringSet(context:Context,key: String,mode:Int = Activity.MODE_PRIVATE): Set<String> {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getStringSet(key, HashSet<String>())
    }

    fun getStringSet(context:Context,key: String): Set<String> {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
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
    fun saveLong(context: Context, key: String, value: Long,mode:Int = Activity.MODE_PRIVATE) {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
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
    fun getLong(context: Context, key: String,mode:Int = Activity.MODE_PRIVATE): Long {
        val sp = context.getSharedPreferences(FILE_NAME, mode)
        return sp.getLong(key, 0)
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

    /**
     * save string array
     * @param context
     * @param key
     * @param value
     */
    fun saveStringArray(context: Context, key: String, value: List<String>) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt("$key-size",value.size)
        for (i in 0 until value.size){
            editor.putString("$key-$i",value[i])
        }
        editor.apply()
    }

    /**
     * save string array
     * @param context
     * @param key
     */
    fun getStringArray(context: Context, key: String): ArrayList<String> {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        val size = sp.getInt("$key-size", 0)
        var list = ArrayList<String>()
        for (i in 0 until size){
            val s = sp.getString("$key-$i", "")
            if (!StringUtils.isEmpty(s)){
                list.add(s)
            }
        }
        return list
    }

    /**
     * Mark in the editor that a preference value should be removed
     * @param context
     * @param key The name of the preference to modify.
     */
    fun removeStringArray(context:Context,key: String) {
        val sp = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        var size = sp.getInt("$key-size",0)
        val editor = sp.edit()
        for (i in 0 until size){
            editor.remove("$key-$i")
        }
        editor.remove("$key-size")
        editor.commit()
    }
}