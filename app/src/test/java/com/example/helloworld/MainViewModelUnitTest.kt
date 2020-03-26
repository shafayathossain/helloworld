package com.example.helloworld

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.helloworld.data.main.model.Message
import com.example.helloworld.data.main.MainRepository
import com.example.helloworld.ui.features.main.MainViewModel
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelUnitTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: MainRepository
    private lateinit var viewModel: MainViewModel



    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            MainViewModel(repository)


        val immediate: Scheduler = object : Scheduler() {

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

//        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.initMainThreadScheduler { immediate }
    }

    @Test
    fun getMessage() {
        val expectedMessage = Message(message = "Hello World!!")

        `when`(repository.getMessage()).thenReturn(Maybe.just(expectedMessage))
        viewModel.getMessage()
        verify(repository, times(1)).getMessage()
        val actualMessage = LiveDataTestUtil<String>().getValue(viewModel.message)

        assertEquals(expectedMessage.message, actualMessage)

    }
}
