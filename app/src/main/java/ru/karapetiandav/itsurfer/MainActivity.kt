package ru.karapetiandav.itsurfer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import kotterknife.bindView
import ru.karapetiandav.itsurfer.service.repository.EventsService

class MainActivity : KodeinAppCompatActivity() {

    // injection
    private val eventsService: EventsService by instance()

    // kotterknife
    private val recyclerView: RecyclerView by bindView(R.id.recyclerview_main)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, FetchEvents::class.java))
    }
}