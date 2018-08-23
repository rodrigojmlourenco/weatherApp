package io.procrastination.foundation.domain.usecases

import io.reactivex.Observable

/**
 * Interface to build Observables.
 * The [ObservableUseCase] must implement this contract.
 */

internal interface ObservableInteractor<T, P> {

    /**
     * Creates an observable with parameters.
     *
     * @param params self-explanatory
     * @return Observable<T> [Observable]
    </T> */
    fun createInteractorObservable(params: P): Observable<T>
}
