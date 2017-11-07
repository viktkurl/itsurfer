package ru.karapetiandav.itsurfer.repository

import android.os.AsyncTask
import org.jsoup.Jsoup
import ru.karapetiandav.itsurfer.model.Event

class FetchEventsTask(val delegate: ASyncResponse) : AsyncTask<Unit, Unit, List<Event>>() {

    override fun doInBackground(vararg params: Unit?): List<Event> {
        val result = mutableListOf<Event>()

        val document = Jsoup.connect(URL).get()
        val elements = document.getElementsByClass("event-list-item")

        // TODO: Надо подумать ещё над парсингом
        elements.forEach {
            val title = it.getElementsByClass("event-list-item__title").text()
            val type = it.getElementsByClass("event-list-item__type").text()
            val date = it.getElementsByClass("event-list-item__info").text()
            val location = it.getElementsByClass("event-list-item__info event-list-item__info_location").text()
            result.add(Event(title, type, date, location))
        }

        return result
    }

    override fun onPostExecute(result: List<Event>?) {
        if (result != null) delegate.eventsReceive(result) else delegate.eventsReceive(emptyList())
    }

    companion object {
        val URL = "https://it-events.com/"
    }
}

interface ASyncResponse {
    fun eventsReceive(events: List<Event>)
}