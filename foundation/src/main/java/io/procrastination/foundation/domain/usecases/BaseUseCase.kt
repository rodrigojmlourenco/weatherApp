package io.procrastination.foundation.domain.usecases


import io.procrastination.foundation.domain.managers.AccountManagerProtocol
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Abstract class for the Base Interactor.
 * Contains the base boilerplate used by all [ObservableInteractor] and
 * [FlowableInteractor]
 *
 *
 * All Observables or Flowables extending from this, will use [CompositeDisposable]
 * for handling the unsubscribe and dispose operations.
 *
 *
 * All Observables or Flowables extending from this, will use [Scheduler] for determining
 * the threads on which the Observables/Flowables subscribe and observe on.
 *
 *
 * Created by Miguel Goncalves on 27/06/2017.
 */
abstract class BaseUseCase
constructor(protected val scheduler: Scheduler) {


    /**
     * Dispose from current [CompositeDisposable].
     *
     * @param disposable self-explanatory
     */
    fun addDisposable(disposable: Disposable) {
        //Ignore
    }
}
