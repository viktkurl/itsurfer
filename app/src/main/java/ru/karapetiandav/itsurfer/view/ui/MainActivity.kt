package ru.karapetiandav.itsurfer.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotterknife.bindView
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.view.adapter.EventsAdapter
import ru.karapetiandav.itsurfer.viewmodel.EventsListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var eventsAdapter: EventsAdapter

    // kotterknife
    private val eventsRecyclerView: RecyclerView by bindView(R.id.recyclerview_main)
    // ----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        val itemDivider = DividerItemDecoration(this, layoutManager.orientation)
        eventsRecyclerView.layoutManager = layoutManager
        eventsRecyclerView.addItemDecoration(itemDivider)

        val viewModel = ViewModelProviders.of(this).get(EventsListViewModel::class.java)
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: EventsListViewModel) {
        viewModel.eventsListObservable.observe(this, Observer<List<Event>> {
            if (it != null) {
                eventsAdapter = EventsAdapter(it)
                eventsRecyclerView.adapter = eventsAdapter
            }
        })
    }
}
