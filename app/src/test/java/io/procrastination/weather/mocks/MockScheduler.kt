package io.procrastination.weather.mocks

import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.schedulers.Schedulers

class MockScheduler : Scheduler {
    override fun getSubscribeOn(): io.reactivex.Scheduler = Schedulers.trampoline()

    override fun getObserveOn(): io.reactivex.Scheduler = Schedulers.trampoline()

    override fun getIoSubscribeOn(): io.reactivex.Scheduler = Schedulers.trampoline()

    override fun getIoObserveOn(): io.reactivex.Scheduler = Schedulers.trampoline()
}