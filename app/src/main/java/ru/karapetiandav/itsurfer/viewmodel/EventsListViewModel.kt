package ru.karapetiandav.itsurfer.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import android.support.annotation.Nullable
import android.util.Log
import ru.karapetiandav.itsurfer.model.Event
import ru.karapetiandav.itsurfer.repository.EventsListRepository
import java.util.concurrent.atomic.AtomicBoolean


class EventsListViewModel(application: Application) : AndroidViewModel(application) {

    var currentPage = 1
        set(value) {
            field = value
            eventsListObservable = EventsListRepository.getEventsList(value)
        }

    var eventsListObservable: LiveData<List<Event>> = EventsListRepository.getEventsList(currentPage)

    var errorObservable: SingleLiveEvent<Pair<String, Int>> = EventsListRepository.error

    val selectedEvent = MutableLiveData<Event>()
    fun select(event: Event) {
        selectedEvent.value = event
    }
}

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer<T> { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {

        private val TAG = "SingleLiveEvent"
    }
}