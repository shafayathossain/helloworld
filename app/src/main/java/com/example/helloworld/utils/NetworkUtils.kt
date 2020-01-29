package com.example.helloworld.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

fun <T>Single<Response<T>>.onResponse(): Single<T> {
    return map {
        if(it.isSuccessful) {
            if(it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}