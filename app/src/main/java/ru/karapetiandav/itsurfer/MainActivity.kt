package ru.karapetiandav.itsurfer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.repository.ASyncResponse
import ru.karapetiandav.itsurfer.repository.FetchEventsTask

class MainActivity : AppCompatActivity(), ASyncResponse {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchEventsTask(this).execute()
    }

    override fun eventsReceive(events: List<Event>) {
        Log.d("MainActivity", events.toString())
    }
}
