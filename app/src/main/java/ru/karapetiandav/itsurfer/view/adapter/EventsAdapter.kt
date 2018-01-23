package ru.karapetiandav.itsurfer.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_event.view.*
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event

interface OnItemClickListener {
    fun onItemClick(event: Event)
}

class EventsAdapter(private val events: List<Event>, private val listener: OnItemClickListener) : RecyclerView.Adapter<EventsAdapter.Companion.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        checkIsOnline(event)
        holder.bind(event, listener)
    }

    private fun checkIsOnline(event: Event) {
        if (event.type?.contains("вебинар", true) != false
                || event.isOnline?.contains("онлайн", true) != false) {
            event.location = event.type
        }
    }

    override fun getItemCount() = events.size

    companion object {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(event: Event, onItemClickListener: OnItemClickListener) {
                itemView.textview_event_item_title.text = event.title
                itemView.textview_event_item_date.text = event.date
                itemView.textview_event_item_location.text = event.location

                itemView.setOnClickListener {
                    onItemClickListener.onItemClick(event)
                }
            }
        }
    }
}