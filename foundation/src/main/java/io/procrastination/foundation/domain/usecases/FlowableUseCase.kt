package io.procrastination.foundation.domain.usecases

import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Abstract class for an FlowableInteractor that uses [Flowable].
 * Represents an execution unit for different interactors, thus any interactors
 * that uses Flowables in the application should implement this contract.
 *
 *
 * By convention it will return the result using a [DisposableSubscriber]
 * that will executeUseCaseInForeground its job in a background thread called [Schedulers.computation]
 * and return the result the UI/Main thread.
 *
 *
 * Created by Miguel Goncalves on 27/06/2017.
 */

abstract class FlowableUseCase<T, P>
constructor(scheduler: Scheduler) : BaseUseCase(scheduler), FlowableInteractor<T, P> {

    /**
     * Builds an [Flowable] which will be used when executing the current [FlowableUseCase].
     *
     * @param params self-explanatory
     * @return Flowable<T>
    </T> */
    protected abstract fun buildInteractorFlowable(params: P): Flowable<T>

    /**
     * Builds an [Flowable] which will be used when executing the current [FlowableUseCase].
     *
     * @param params self-explanatory
     * @return Flowable<T>
    </T> */
    override fun createInteractorFlowable(params: P): Flowable<T> {
        return buildInteractorFlowable(params)
    }

    /**
     * Executes the current interactors using a [Flowable] by subscribing on the
     * [Scheduler.getSubscribeOn] and observing on the [Scheduler.getObserveOn] threads.
     *
     * @param subscriber [DisposableSubscriber] the subscriber
     * @param params self-explanatory
     */
    fun execute(subscriber: DisposableSubscriber<T>, params: P) {
        // Preconditions.checkNotNull(subscriber);
        val flowable = buildInteractorFlowable(params)
                .subscribeOn(scheduler.getSubscribeOn())
                .observeOn(scheduler.getObserveOn())
        addDisposable(flowable.subscribeWith(subscriber))
    }
}
