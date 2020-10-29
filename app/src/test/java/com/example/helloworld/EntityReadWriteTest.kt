package com.example.helloworld

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.main.model.Message
import com.example.helloworld.data.main.MainRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {

    @Mock
    private lateinit var networkSource: MessageNetworkSource
    @Mock
    private lateinit var preference: AppPreference
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        MockitoAnnotations.initMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testIfMessageInserted() {
        val repository = MainRepositoryImpl(
            networkSource,
            db,
            preference
        )
        val expectedDataFromNetwork = Message(message = "Hello World!")
        var testResponse: Message
        runBlocking{
            Mockito.`when`(networkSource.getMessage()).thenReturn(Response.success(expectedDataFromNetwork))

            testResponse = repository.getMessage()
            assertThat(testResponse.message).isEqualTo(expectedDataFromNetwork.message)

            testResponse = db.messageDao().getMessage()
            assertThat(testResponse.message).isEqualTo(expectedDataFromNetwork.message)
        }


    }
}