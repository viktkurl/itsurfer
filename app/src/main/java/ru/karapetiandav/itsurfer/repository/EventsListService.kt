package ru.karapetiandav.itsurfer.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.karapetiandav.itsurfer.model.EventsPage

interface EventsListService {
    @GET("/")
    fun getPage(@Query("page") page: Int): Call<EventsPage>
}