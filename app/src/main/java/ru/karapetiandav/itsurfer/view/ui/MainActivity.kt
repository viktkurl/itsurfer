package ru.karapetiandav.itsurfer.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.karapetiandav.itsurfer.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_activity_container, EventsListFragment())
            .commit()
    }

}
