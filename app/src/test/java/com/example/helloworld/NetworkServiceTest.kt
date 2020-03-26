package com.example.helloworld

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helloworld.core.data.network.NetworkFactory
import com.example.helloworld.data.datasources.networksource.MessageNetworkService
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class NetworkServiceTest {

    private var mockWebServer = MockWebServer()
    private lateinit var messageNetworkService: MessageNetworkService

    @Before
    fun setup() {

        val dispatcher = object: Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                print(request.path)
                return when(request.path) {
                    "/resources/helloworld.json" -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody("{\"message\": \"Hello World\"}")
                    }
                    else -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody("{\"message\": \"Hello World!!!!!\"}")
                    }
                }
            }

        }
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        messageNetworkService = NetworkFactory.getRetrofit(context,
            mockWebServer.url("/").toUri().toString(),
            NetworkFactory.getOkHttpClient(NetworkFactory.getAuthInterceptor(context), NetworkFactory.getLogInterceptors(), context))
            .create(MessageNetworkService::class.java)
    }

    @After
    fun closeServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun testMessageResponse() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"message\": \"Hello World\"}")
//        mockWebServer.enqueue(response)
        val message = messageNetworkService.getMessage().blockingGet()
        assert(message.body()?.message == "Hello World")
    }
}