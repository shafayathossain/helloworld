package com.example.helloworld.data.datasources.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.helloworld.R
import com.example.helloworld.di.annotations.ApplicationContext
import com.google.gson.Gson
import javax.inject.Inject


class AppPreferenceImpl @Inject constructor(@ApplicationContext context: Context) :
    AppPreference {

    private var preference =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    private var editor = preference.edit()

    override fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return preference.getString(key, "") ?: ""
    }

    override fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }

    override fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    override fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }

    override fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    override fun saveObject(key: String, value: Any) {
        val valueString = Gson().toJson(value)
        saveString(key, valueString)
    }

    override fun <T>getObject(key: String, clazz: Class<T>): T {
        return Gson().fromJson<T>(preference.getString(key, "{}"), clazz)
    }

}