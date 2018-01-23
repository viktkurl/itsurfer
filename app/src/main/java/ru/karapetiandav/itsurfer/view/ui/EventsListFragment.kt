package ru.karapetiandav.itsurfer.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_events_list.*
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.repository.EventsListRepository.Companion.PAGE_SIZE
import ru.karapetiandav.itsurfer.repository.EventsListRepository.Companion.isLastPage
import ru.karapetiandav.itsurfer.repository.EventsListRepository.Companion.isLoading
import ru.karapetiandav.itsurfer.view.adapter.EventsAdapter
import ru.karapetiandav.itsurfer.view.adapter.OnItemClickListener
import ru.karapetiandav.itsurfer.viewmodel.EventsListViewModel

class EventsListFragment : Fragment() {

    private var positionToScroll = 0

    private lateinit var eventsAdapter: EventsAdapter
    private var myRoot: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (myRoot == null) myRoot = inflater.inflate(R.layout.fragment_events_list, container, false)
        return myRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = activity!!.let { ViewModelProviders.of(it).get(EventsListViewModel::class.java) }
        observeViewModel(viewModel)

        val layoutManager = LinearLayoutManager(context)
        val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
        recyclerviewEventsList.layoutManager = layoutManager
        recyclerviewEventsList.addItemDecoration(itemDivider)
        recyclerviewEventsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        viewModel.currentPage += 1
                        positionToScroll = firstVisibleItemPosition
                    }
                }
            }
        })
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
                recyclerviewEventsList.adapter = eventsAdapter
                recyclerviewEventsList.scrollToPosition(positionToScroll + 1)
            }
        })

        viewModel.errorObservable.observe(activity!!, Observer {
            if (it != null) {
                Toast.makeText(activity!!, it.first, Toast.LENGTH_SHORT).show()
                imageviewErrorList.setImageDrawable(ContextCompat.getDrawable(activity!!, it.second))
            }
        })
    }
}