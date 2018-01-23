package ru.karapetiandav.itsurfer.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.model.EventsPage
import ru.karapetiandav.itsurfer.viewmodel.SingleLiveEvent

class EventsListRepository {

    companion object {
        private val TAG = EventsListRepository::class.java.simpleName

        private val URL = "https://it-events.com/"

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(JspoonConverterFactory.create())
                    .build()
        }

        private fun createEventsListService(): EventsListService = createRetrofit().create(EventsListService::class.java)

        private val events: MutableList<Event> = mutableListOf()
        private val data: MutableLiveData<List<Event>> = MutableLiveData()
        val error: SingleLiveEvent<Pair<String, Int>> = SingleLiveEvent()

        var isLoading = false
        var isLastPage = false
        const val PAGE_SIZE = 20

        fun getEventsList(page: Int): LiveData<List<Event>> {
            isLoading = true

            createEventsListService().getPage(page).enqueue(object : Callback<EventsPage> {
                override fun onResponse(call: Call<EventsPage>?, response: Response<EventsPage>?) {
                    isLoading = false
                    if (response?.body()?.events != null) {
                        events.addAll(response.body()?.events!!)

                        if (response.body()?.events?.size!! < PAGE_SIZE) {
                            isLastPage = true
                        }
                    }

                    data.value = events
                }

                override fun onFailure(call: Call<EventsPage>?, t: Throwable?) {
                    Log.d(TAG, t?.message)
                    data.value = emptyList()
                    isLoading = false
                    error.value = Pair("Не удалось соединиться с сервером", R.drawable.oops_error)
                }
            })

            return data
        }
    }
}