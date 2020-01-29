package com.example.helloworld.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable
import okhttp3.ResponseBody


fun isConnected(appContext: Context): Boolean {
    val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun <T> Observable<T>.onException(appContext: Context): Observable<T> {
    return this.onErrorResumeNext { it: Throwable ->
        Observable.create<T> { emitter ->
            if (it is ApiException) {
                emitter.onError(parseRequestException(appContext, it.code, it.errorBody, it.message))
            } else {
                emitter.onError(parseIOException(appContext))
            }
        }
    }
}

fun parseIOException(appContext: Context, throwable: Throwable? = null): RequestException {
    return if (!isConnected(appContext)) {
        RequestException(message = "Slow or no internet connection")
    } else {
        RequestException(message ="Unknown exception"))
    }
}

/**
 * parse [RequestException] from [okhttp3.OkHttpClient] response
 */
fun parseRequestException(appContext: Context, code: Int, errorBody: ResponseBody? = null, message: String? = null): RequestException {
    errorBody?.let { body ->
        // parse error model from response
        val requestError: ApiError = getConverter(appContext, ApiError::class.java, body)

        // if error response does not contain any specific message use a generic error message from resource
        return RequestException().apply {
            this.message = if (requestError.message.isBlank()) {
                appContext.getString(R.string.msg_failed_try_again)
            } else {
                requestError.message
            }

            httpCode = code
        }
    }

    message?.let {msg ->
        return RequestException(message = msg)
    }

    return RequestException(message = appContext.getString(R.string.msg_failed_try_again))
}

class RequestException(var httpCode: Int = 500, override var message: String = "") : Exception(message)

class ApiException(val code: Int, val errorBody: ResponseBody?, override val message: String) : Exception(message)
