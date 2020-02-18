package com.example.helloworld

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helloworld.core.localdb.AppDatabase
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.data.datasources.preference.AppPreference
import com.example.helloworld.data.model.Message
import com.example.helloworld.data.repository.main.MainRepositoryImpl
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
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

        val immediate: Scheduler = object : Scheduler() {

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.initMainThreadScheduler { immediate }
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testIfMessageInserted() {
        var repository = MainRepositoryImpl(networkSource, db, preference)
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