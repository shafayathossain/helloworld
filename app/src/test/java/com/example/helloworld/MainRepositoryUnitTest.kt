package com.example.helloworld

import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.core.data.network.RetrofitException
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.datasources.localdb.MessageDao
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.data.main.MainRepositoryImpl
import com.example.helloworld.data.main.model.Message
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.internal.wait
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.lang.reflect.Method
import kotlin.math.exp


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
    @PrepareForTest(MainRepositoryImpl::class)
    fun testGetMessage() {

        val expectedDataFromNetwork = Message(message = "Hello World!")
        val testObserver = TestObserver.create<Message>()
        `when`(db.messageDao()).thenReturn(dao)

        `when`(networkSource.getMessage()).thenReturn(Maybe.just(expectedDataFromNetwork))
        repository.getMessage()
            .subscribe(testObserver)

        testObserver
            .assertValue {
                it.message == expectedDataFromNetwork.message
            }
            .dispose()

        val spyRepository: MainRepositoryImpl = PowerMockito.spy(repository)
        PowerMockito.verifyPrivate(spyRepository)
            .invoke("saveAndReturnMessage", expectedDataFromNetwork)

    }

    @Test
    @PrepareForTest(MainRepositoryImpl::class)
    fun testGetMessageOnError() {
        val expectedDataFromNetwork = Message(message = "Hello World!")
        `when`(db.messageDao()).thenReturn(dao)
        `when`(dao.getMessage()).thenReturn(Single.create { it.onSuccess(expectedDataFromNetwork) })
        `when`(networkSource.getMessage()).thenReturn(
            Maybe.error(
                RetrofitException(
                    "Failed 1",
                    "",
                    null,
                    RetrofitException.Kind.NETWORK,
                    null,
                    null
                )
            )
        )
        val getMessageFromDbMethod: Method = repository.javaClass
            .getDeclaredMethod("getMessageFromPreference")

        getMessageFromDbMethod.isAccessible = true
        val testObserver = TestObserver.create<Message>()
        repository.getMessage()
                .subscribe(testObserver)
        val spyRepository: MainRepositoryImpl = PowerMockito.spy(repository)
        PowerMockito.verifyPrivate(spyRepository, atLeast(3))
                .invoke(getMessageFromDbMethod)
    }

    @Test
    @PrepareForTest(MainRepositoryImpl::class)
    fun testGetMessageFromDbOnSuccess() {
        val expectedDataFromDb = Message(message = "Hello World")
        val getMessageFromDbMethod: Method = repository.javaClass
            .getDeclaredMethod("getMessageFromDb")
        getMessageFromDbMethod.isAccessible = true
        `when`(db.messageDao()).thenReturn(dao)
        `when`(dao.getMessage()).thenReturn(Single.create { it.onSuccess(expectedDataFromDb) })

        val testObserver = TestObserver.create<Message>()
        (getMessageFromDbMethod.invoke(repository) as Maybe<Message>).subscribe(testObserver)
        testObserver
            .assertValue{
                it.message == expectedDataFromDb.message
            }
            .dispose()
    }

    @Test
    @PrepareForTest(MainRepositoryImpl::class)
    fun testGetMessageFromDbOnError() {

        val getMessageFromDbMethod: Method = repository.javaClass
            .getDeclaredMethod("getMessageFromDb")
        val getMessageFromPreferenceMethod: Method = repository.javaClass
            .getDeclaredMethod("getMessageFromPreference")
            .apply {
                isAccessible = true
            }
        getMessageFromDbMethod.isAccessible = true
        `when`(db.messageDao()).thenReturn(dao)
        `when`(dao.getMessage()).thenReturn(Single.create { it.onError(Throwable("Test exception")) })

        val spyRepository = PowerMockito.spy(repository)
        PowerMockito.verifyPrivate(spyRepository, atMost(0))
                .invoke(getMessageFromPreferenceMethod)
    }

    @Test
    fun testGetMessageFromPreference() {
        val expectedDataFromPreference = "Hello World"
        val testObserver = TestObserver.create<Message>()
        val getMessageFromPreferenceMethod: Method = repository.javaClass
            .getDeclaredMethod("getMessageFromPreference")
        getMessageFromPreferenceMethod.isAccessible = true
        `when`(preference.message).thenReturn(expectedDataFromPreference)

        (getMessageFromPreferenceMethod.invoke(repository) as Single<Message>)
            .subscribe(testObserver)

        testObserver
            .assertValue{
                it.message == expectedDataFromPreference
            }
            .dispose()
    }

    @Test
     fun testSaveAndReturnMessage() {
        val expectedMessage = Message(message = "Hello World")
        val saveAndReturnMessageMethod: Method = repository.javaClass
            .getDeclaredMethod("saveAndReturnMessage", Message::class.java)
        saveAndReturnMessageMethod.isAccessible = true
        `when`(db.messageDao()).thenReturn(dao)
        saveAndReturnMessageMethod.invoke(repository, expectedMessage)
        verify(dao, times(1)).insertMessage(captor.capture())
        assertEquals(expectedMessage, captor.firstValue)
     }

    @Test
    fun testSaveAndReturnMessageForEmptyMessage() {
        val expectedMessage = Message(message = MainRepositoryImpl.DEFAULT_MESSAGE)
        val pMessage = Message()
        val saveAndReturnMessageMethod: Method = repository.javaClass
            .getDeclaredMethod("saveAndReturnMessage", Message::class.java)
        saveAndReturnMessageMethod.isAccessible = true
        `when`(db.messageDao()).thenReturn(dao)
        saveAndReturnMessageMethod.invoke(repository, pMessage)
        verify(dao, times(1)).insertMessage(captor.capture())
        assertEquals(expectedMessage, captor.firstValue)
    }

}