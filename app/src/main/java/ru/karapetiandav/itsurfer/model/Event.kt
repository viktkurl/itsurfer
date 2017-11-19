package ru.karapetiandav.itsurfer.model

import pl.droidsonroids.jspoon.annotation.Selector

class Event {
    @Selector(".event-list-item__title")
    var title: String? = null

    @Selector(".event-list-item__type")
    var type: String? = null

    @Selector(".event-list-item__info")
    var date: String? = null

    @Selector(".event-list-item__info_location")
    var location: String? = null
}