package ru.karapetiandav.itsurfer.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_event_details.*
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.viewmodel.EventsListViewModel

class EventDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!! as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activity?.let {
            val viewModel = ViewModelProviders.of(it).get(EventsListViewModel::class.java)
            viewModel.selectedEvent.observe(this, Observer {
                textview_event_details_title.text = it?.title
                textview_event_details_date.text = it?.date
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity!!.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        val EVENT_DETAILS_FRAGMENT_TAG = "event_details_fragment_tag"
    }
}
