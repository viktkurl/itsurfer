package ru.karapetiandav.itsurfer.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.repository.EventsListRepository

class EventsListViewModel(application: Application) : AndroidViewModel(application) {

    val eventsListObservable: LiveData<List<Event>> = EventsListRepository.getEventsList(1)

}