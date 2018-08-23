package io.procrastination.foundation.domain.schedueler

interface Scheduler {

    /**
     * Returns the subscribed thread
     *
     * @return io.reactivex.Scheduler
     * @see io.reactivex.Scheduler
     *
     * @see io.reactivex.Observable.subscribeOn
     */
    fun getSubscribeOn(): io.reactivex.Scheduler

    /**
     * Returns the observed on thread
     *
     * @return io.reactivex.Scheduler
     * @see io.reactivex.Scheduler
     *
     * @see io.reactivex.Observable.observeOn
     */
    fun getObserveOn(): io.reactivex.Scheduler

    /**
     * Returns the IO subscribed thread
     *
     * @return io.reactivex.Scheduler
     * @see io.reactivex.Scheduler
     *
     * @see io.reactivex.Observable.subscribeOn
     */
    fun getIoSubscribeOn(): io.reactivex.Scheduler

    /**
     * Returns the IO observed on thread
     *
     * @return io.reactivex.Scheduler
     * @see io.reactivex.Scheduler
     *
     * @see io.reactivex.Observable.observeOn
     */
    fun getIoObserveOn(): io.reactivex.Scheduler
}