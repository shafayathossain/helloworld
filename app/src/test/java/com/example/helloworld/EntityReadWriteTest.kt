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
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
        var repository = MainRepositoryImpl(
            networkSource,
            db,
            preference
        )
        val expectedDataFromNetwork = Message(message = "Hello World!!")
        var testObserver = TestObserver.create<Message>()
        Mockito.`when`(networkSource.getMessage()).thenReturn(Maybe.just(expectedDataFromNetwork))

        repository.getMessage()
            .subscribe(testObserver)
        testObserver.assertValue {
            it.message == expectedDataFromNetwork.message
        }
            .dispose()

        testObserver = TestObserver.create<Message>()
        db.messageDao().getMessage()
            .subscribe(testObserver)
        testObserver
            .assertValue {
            print(it)
            it.message == expectedDataFromNetwork.message
        }
    }
}