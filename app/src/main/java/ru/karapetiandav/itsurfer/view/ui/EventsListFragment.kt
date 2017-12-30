package ru.karapetiandav.itsurfer.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotterknife.bindView
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.view.adapter.EventsAdapter
import ru.karapetiandav.itsurfer.view.adapter.OnItemClickListener
import ru.karapetiandav.itsurfer.viewmodel.EventsListViewModel

class EventsListFragment : Fragment() {

    // kotterknife
    private val eventsRecyclerView: RecyclerView by bindView(R.id.recyclerview_events_list)
    // ----

    private lateinit var eventsAdapter: EventsAdapter
    private var myRoot: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (myRoot == null) myRoot = inflater.inflate(R.layout.fragment_events_list, container, false)
        return myRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
        eventsRecyclerView.layoutManager = layoutManager
        eventsRecyclerView.addItemDecoration(itemDivider)

        val viewModel = activity!!.let { ViewModelProviders.of(it).get(EventsListViewModel::class.java) }
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: EventsListViewModel) {
        viewModel.eventsListObservable.observe(activity!!, Observer {
            it?.let {
                eventsAdapter = EventsAdapter(it, object : OnItemClickListener {
                    override fun onItemClick(event: Event) {
                        activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_activity_container, EventDetailsFragment())
                            .addToBackStack(EventDetailsFragment.EVENT_DETAILS_FRAGMENT_TAG)
                            .commit()

                        viewModel.select(event)
                    }
                })
                eventsRecyclerView.adapter = eventsAdapter
            }
        })
    }
}
