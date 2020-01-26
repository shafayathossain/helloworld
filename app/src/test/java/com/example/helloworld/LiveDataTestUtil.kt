package com.example.helloworld

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

public class LiveDataTestUtil<T> {

    public fun <T>getValue(liveData: LiveData<T>): T? {

        val data = ArrayList<T>()

        // latch for blocking thread until data is set
        val latch = CountDownLatch(1)

        val observer: Observer<T> = object: Observer<T>{
            override fun onChanged(t: T) {
                data.add(t)
                latch.countDown() // release the latch
                liveData.removeObserver(this)
            }

        }
        liveData.observeForever(observer)
        try {
            latch.await(2, TimeUnit.SECONDS) // wait for onChanged to fire and set data
        } catch (e: InterruptedException) {
            throw InterruptedException("Latch failure")
        }
        if(data.size > 0){
            return data.get(0)
        }
        return null
    }

}