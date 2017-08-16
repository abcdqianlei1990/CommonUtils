package com.channey.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Created by channey on 2017/8/1.
 */
object JsonUtils {
    /**
     * 将json数组字符串转为ArrayList<T>
     * @param json the string from which the object is to be deserialized
     * *
     * @param clazz the class of T
     * *
     * @param <T> the type of the desired object
     * *
     * @return
    </T></T> */
    fun <T> jsonToArrayList(json: String, clazz: Class<T>): ArrayList<T> {
        val type = object : TypeToken<ArrayList<JsonObject>>() {

        }.type
        val jsonObjects = Gson().fromJson<ArrayList<JsonObject>>(json, type)

        val arrayList = ArrayList<T>()
        for (jsonObject in jsonObjects) {
            arrayList.add(Gson().fromJson(jsonObject, clazz))
        }
        return arrayList
    }

    /**
     * 将json转为指定对象
     * @param json the string from which the object is to be deserialized
     * *
     * @param classOfT the class of T
     * *
     * @param <T> the type of the desired object
     * *
     * @return
    </T> */
    fun <T> json2Object(json: String, classOfT: Class<T>): T {
        val gson = Gson()
        val obj = gson.fromJson(json, classOfT)
        return obj
    }

    /**
     * 将对象转为json字符串
     * @param src the object for which Json representation is to be created setting for Gson
     * *
     * @return
     */
    fun toJson(src: Any): String {
        val gson = Gson()
        return gson.toJson(src)
    }
}