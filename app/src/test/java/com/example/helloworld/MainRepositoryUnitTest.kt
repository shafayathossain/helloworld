package com.example.helloworld

import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.core.data.network.RetrofitException
import com.example.helloworld.data.datasources.localdb.MessageDao
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.main.model.Message
import com.example.helloworld.data.main.MainRepositoryImpl
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


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

    @Test
    fun testGetMessage() {

        // Check if data after network call success is correct

        val expectedDataFromNetwork = Message(message = "Hello World!")
        val expectedDataFromDb = Message(message = "Hello World")
        var testObserver = TestObserver.create<Message>()
        `when`(db.messageDao()).thenReturn(dao)

        `when`(networkSource.getMessage()).thenReturn(Maybe.just(expectedDataFromNetwork))
        repository.getMessage()
            .subscribe(testObserver)

        testObserver
            .assertValue {
                it.message == expectedDataFromNetwork.message
            }
            .dispose()

        // check if data after network call success and fetch from local db is correct
        testObserver = TestObserver.create<Message>()
        `when`(networkSource.getMessage()).thenReturn(Maybe.error(RetrofitException(
            "Failed 1",
            "",
            null,
            RetrofitException.Kind.NETWORK,
            null,
            null
        )))
        `when`(dao.getMessage()).thenReturn(Single.create { it.onSuccess(expectedDataFromDb) })
        repository.getMessage()
            .subscribe(testObserver)

        testObserver
            .assertValue{
                it.message == expectedDataFromDb.message
            }
            .dispose()
        verify(dao).getMessage()

        // check if network call and local db data fetch failed, then data from preference is correct
        testObserver = TestObserver.create<Message>()

        `when`(networkSource.getMessage()).thenReturn(Maybe.error(RetrofitException(
            "Failed 2",
            "",
            null,
            RetrofitException.Kind.NETWORK,
            null,
            null
        )))
        `when`(dao.getMessage()).thenReturn(Single.create { it.onError(Throwable("Test error")) })
        `when`(preference.message).thenReturn(expectedDataFromDb.message)

        repository.getMessage()
            .subscribe(testObserver)

        testObserver
            .assertValue{
                it.message == expectedDataFromDb.message
            }
            .dispose()


        // check if network call, local db data fetching failed and preference has no data,
        // then default "Hello World!" is returned or not

        testObserver = TestObserver.create<Message>()
        `when`(networkSource.getMessage()).thenReturn(Maybe.error(RetrofitException(
            "",
            "",
            null,
            RetrofitException.Kind.NETWORK,
            null,
            null
        )))
        `when`(dao.getMessage()).thenReturn(Single.create { it.onError(Throwable("Test error")) })
        `when`(preference.message).thenReturn("")
        repository.getMessage()
            .subscribe(testObserver)

        testObserver
            .assertValue{
                it.message == "Hello World!"
            }
            .dispose()

        // verify if data inserted into local db
        verify(dao, times(4)).insertMessage(captor.capture())
        assertEquals(expectedDataFromNetwork, captor.firstValue)
        assertEquals(expectedDataFromDb, captor.secondValue)
        assertEquals(expectedDataFromDb, captor.thirdValue)
        assertEquals(Message(message = "Hello World!"), captor.allValues[3])
    }

}