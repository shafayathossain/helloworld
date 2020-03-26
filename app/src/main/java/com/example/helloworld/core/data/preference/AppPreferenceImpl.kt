package com.example.helloworld.core.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.helloworld.R
import com.example.helloworld.di.annotations.ApplicationContext
import com.google.gson.Gson
import javax.inject.Inject


class AppPreferenceImpl @Inject constructor(@ApplicationContext context: Context) :
    AppPreference {

    companion object {
        const val MESSAGE = "message"
    }

    private var preference =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    private var editor = preference.edit()

    override var message: String
        get() = getString(MESSAGE)
        set(value) {
            saveString(MESSAGE, value)
        }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }

    private fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    private fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }

    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    private fun saveObject(key: String, value: Any) {
        val valueString = Gson().toJson(value)
        saveString(key, valueString)
    }

    private fun <T>getObject(key: String, clazz: Class<T>): T {
        return Gson().fromJson<T>(preference.getString(key, "{}"), clazz)
    }

}