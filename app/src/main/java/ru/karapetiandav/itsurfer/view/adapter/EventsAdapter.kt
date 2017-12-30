package ru.karapetiandav.itsurfer.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotterknife.bindView
import ru.karapetiandav.itsurfer.R
import ru.karapetiandav.itsurfer.model.Event

interface OnItemClickListener {
    fun onItemClick(event: Event)
}

class EventsAdapter(private val events: List<Event>, private val listener: OnItemClickListener) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        checkIsOnline(event)
        holder.eventTitle.text = event.title
        holder.eventDate.text = event.date
        holder.eventLocation.text = event.location

        holder.bind(event, listener)
    }

    private fun checkIsOnline(event: Event) {
        if (event.type?.contains("вебинар", true) != false
            || event.isOnline?.contains("онлайн", true) != false) {
            event.location = event.type
        }
    }

    override fun getItemCount() = events.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView by bindView(R.id.textview_event_item_title)
        val eventDate: TextView by bindView(R.id.textview_event_item_date)
        val eventLocation: TextView by bindView(R.id.textview_event_item_location)

        fun bind(event: Event, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(event)
            }
        }
    }
}