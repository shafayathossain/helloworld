package com.example.helloworld.data.datasources.preference

interface AppPreference {

    fun saveString(key: String, value: String)

    fun getString(key: String): String

    fun saveBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    fun saveInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int = 0): Int

    fun saveFloat(key: String, value: Float)

    fun getFloat(key: String, defaultValue: Float = 0f): Float

    fun saveLong(key: String, value: Long)

    fun getLong(key: String, defaultValue: Long = 0L): Long

    fun saveObject(key: String, value: Any)

    fun <T>getObject(key: String, clazz: Class<T>): T
}