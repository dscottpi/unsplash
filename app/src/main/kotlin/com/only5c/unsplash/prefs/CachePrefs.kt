package com.only5c.unsplash.prefs

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.only5c.unsplash.PHOTOS_CACHE
import com.only5c.unsplash.PHOTOS_CACHE_HOME
import com.only5c.unsplash.PHOTOS_CACHE_NEW
import com.only5c.unsplash.models.Photo
import java.lang.reflect.Type
import java.util.*

fun setPreferenceString(context: Context, preferencesName: String, key: String, value: String) {
    val settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getPreferenceString(context: Context, preferencesName: String, key: String, defaultValue: String): String {
    val settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    return settings.getString(key, defaultValue)
}

fun getPreferenceString(context: Context, preferencesName: String, key: String): String {
    return getPreferenceString(context, preferencesName, key, Gson().toJson(ArrayList<Photo>()))
}

fun <T> getPreferenceJsonList(context: Context, preferenceName: String, key: String, typeOf: Type): ArrayList<T> {
    val jsonString = getPreferenceString(context, preferenceName, key)
    return Gson().fromJson<ArrayList<T>>(jsonString, typeOf)
}

fun setPreferenceJson(context: Context, preferencesName: String, key: String, src: Any) {
    val json = Gson().toJson(src)
    setPreferenceString(context, preferencesName, key, json)
}

fun getHomePhotos(context: Context) : ArrayList<Photo> {
    return getPreferenceJsonList(context, PHOTOS_CACHE, PHOTOS_CACHE_HOME,
            object : TypeToken<ArrayList<Photo>>() {}.type)
}

fun getNewPhotos(context: Context): ArrayList<Photo> {
    return getPreferenceJsonList(context, PHOTOS_CACHE, PHOTOS_CACHE_NEW,
            object : TypeToken<ArrayList<Photo>>() {}.type)
}

fun updateHomePhotos(context: Context, photos: ArrayList<Photo>) {
    setPreferenceJson(context, PHOTOS_CACHE, PHOTOS_CACHE_HOME, photos)
}

fun updateNewPhotos(context: Context, photos: ArrayList<Photo>) {
    setPreferenceJson(context, PHOTOS_CACHE, PHOTOS_CACHE_NEW, photos)
}
