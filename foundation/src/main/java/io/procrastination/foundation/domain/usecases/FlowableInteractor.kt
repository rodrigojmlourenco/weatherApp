package io.procrastination.foundation.domain.usecases

import io.reactivex.Flowable

/**
 * Interface to build Flowables.
 * The [FlowableUseCase] must implement this contract.
 */

internal interface FlowableInteractor<T, P> {

    /**
     * Creates a flowable with parameters.
     *
     * @param params self-explanatory
     * @return Flowable<T> [Flowable]
    </T> */
    fun createInteractorFlowable(params: P): Flowable<T>
}
