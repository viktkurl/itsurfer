package ru.karapetiandav.itsurfer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.karapetiandav.itsurfer.service.model.Event

class EventsAdapter(private val eventsList: List<Event>) : RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder?, position: Int) {
        if (holder != null) {
            val event = eventsList[position]
            holder.title.text = event.title
            holder.date.text = event.date
            holder.location.text = event.location
        }
    }

    override fun getItemCount(): Int = eventsList.size
}

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.textview_event_title)
    val date: TextView = itemView.findViewById(R.id.textview_event_date)
    val location: TextView = itemView.findViewById(R.id.textview_event_location)
}