package com.example.helloworld

import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.data.datasources.localdb.MessageDao
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.main.model.Message
import com.example.helloworld.data.main.MainRepositoryImpl
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response


class MainRepositoryUnitTest {

    @Mock
    private lateinit var networkSource: MessageNetworkSource
    @Mock
    private lateinit var db: AppDatabase

    @Mock
    private lateinit var dao: MessageDao

    @Mock
    private lateinit var preference: AppPreference

    private lateinit var repository: MainRepositoryImpl
    private lateinit var captor: KArgumentCaptor<Message>

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        captor = KArgumentCaptor(ArgumentCaptor.forClass(Message::class.java), Message::class)
        repository = MainRepositoryImpl(
            networkSource,
            db,
            preference
        )
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGetMessage() = runBlocking {
        // Check if data after network call success is correct
        val expectedDataFromNetwork = Message(message = "Hello World!")
        val expectedDataFromDb = Message(message = "Hello World!")
        var testMessage : Message
        `when`(db.messageDao()).thenReturn(dao)

        `when`(networkSource.getMessage()).thenReturn(Response.success(expectedDataFromNetwork))

        testMessage = repository.getMessage()
        assertThat(testMessage.message).isEqualTo(expectedDataFromNetwork.message)


        // check if data after network call fail and fetch from local db is correct
        `when`(networkSource.getMessage()).thenReturn(Response.error(
                400,
                "{\"message\":[\"Failed Request\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
        ))
        `when`(dao.getMessage()).thenReturn(expectedDataFromDb)

        testMessage = repository.getMessage()
        assertThat(testMessage.message).isEqualTo(expectedDataFromDb.message)
        /*launch{ verify(dao).getMessage() }.join()*/

        // check if network call and local db data fetch failed, then data from preference is correct
        `when`(networkSource.getMessage()).thenReturn(Response.error(
                400,
                "{\"message\":[\"Failed Request\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
        ))
        `when`(dao.getMessage()).thenReturn(null)
        `when`(preference.message).thenReturn(expectedDataFromDb.message)

        testMessage = repository.getMessage()
        assertThat(testMessage.message).isEqualTo(expectedDataFromDb.message)


        // check if network call, local db data fetching failed and preference has no data,
        // then default "Hello World!" is returned or not
        `when`(networkSource.getMessage()).thenReturn(Response.error(
                400,
                "{\"message\":[\"Failed Request\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
        ))
        `when`(dao.getMessage()).thenReturn(null)
        `when`(preference.message).thenReturn("")

        testMessage = repository.getMessage()
        assertThat(testMessage.message).isEqualTo("Hello World!")

        // verify if data inserted into local db
        /*launch{
            verify(dao, times(4)).insertMessage(captor.capture())
        }.join()
        assertEquals(expectedDataFromNetwork, captor.firstValue)
        assertEquals(expectedDataFromDb, captor.secondValue)
        assertEquals(expectedDataFromDb, captor.thirdValue)
        assertEquals(Message(message = "Hello World!"), captor.allValues[3])*/
    }


}
