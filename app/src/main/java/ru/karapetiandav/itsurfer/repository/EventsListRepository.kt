package ru.karapetiandav.itsurfer.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.model.EventsPage

private val TAG = EventsListRepository::class.simpleName

class EventsListRepository {

    companion object {

        val URL = "https://it-events.com/"

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(JspoonConverterFactory.create())
                .build()
        }

        private fun createEventsListService(): EventsListService = createRetrofit().create(EventsListService::class.java)

        fun getEventsList(page: Int): LiveData<List<Event>> {
            val data: MutableLiveData<List<Event>> = MutableLiveData()

            createEventsListService().getPage(page).enqueue(object : Callback<EventsPage> {
                override fun onResponse(call: Call<EventsPage>?, response: Response<EventsPage>?) {
                    data.value = response?.body()?.events
                }

                override fun onFailure(call: Call<EventsPage>?, t: Throwable?) {
                    Log.d(TAG, t?.message)
                    data.value = emptyList()
                }
            })

            return data
        }
    }
}