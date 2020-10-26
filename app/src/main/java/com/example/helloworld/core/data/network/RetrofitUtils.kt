package com.example.helloworld.core.data.network

import retrofit2.Response
import java.lang.Exception

// extension function for handling error in retrofit response
fun <T> Response<T>.responseOrException(): Response<T> {
    if (isSuccessful) {
        val body = body()
        return if (body != null) {
            Response.success(body)
        } else {
            throw Exception("Request Exception")
        }
    }else throw Exception(message())
}