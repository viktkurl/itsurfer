package ru.karapetiandav.itsurfer.model

import pl.droidsonroids.jspoon.annotation.Selector

class EventsPage {
    @Selector(".event-list-item")
    var events: List<Event>? = null
}
