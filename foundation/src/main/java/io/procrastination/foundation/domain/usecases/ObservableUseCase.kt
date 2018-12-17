package io.procrastination.foundation.domain.usecases

import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Abstract class for an [ObservableUseCase] that uses [Observable].
 * Represents an execution unit for different interactors, thus any interactors
 * that uses Observables in the application should implement this contract.
 *
 *
 * By convention it will return the result using a [DisposableObserver]
 * that will executeUseCaseInForeground its job in a background thread called [Schedulers.computation]
 * and return the result the UI/Main thread.
 *
 * @param T Response
 * @param P Parameters
 * */
abstract class ObservableUseCase<T, P>
constructor(scheduler: Scheduler) : BaseUseCase(scheduler), ObservableInteractor<T, P> {

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     *
     * @param params self-explanatory
     * @return Observable<T>
     */
    protected abstract fun buildUseCaseObservable(params: P): Observable<T>

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     *
     * @param params self-explanatory
     * @return Observable<T>
     */
    override fun createInteractorObservable(params: P): Observable<T> {
        return buildUseCaseObservable(params)
    }

    fun execute(
        params: P,
        filter: Predicate<T>? = Predicate { true },
        observer: Consumer<T>,
        onError: Consumer<Throwable>,
        onComplete: Action
    ): Disposable {

        return buildUseCaseObservable(params)
            .filter(filter)
            .subscribeOn(scheduler.getSubscribeOn())
            .observeOn(scheduler.getObserveOn())
            .subscribe(observer, onError, onComplete)
    }

    @Suppress("UNUSED_PARAMETER")
    protected fun buildValidationError(params: P): Observable<*> {
        return Observable.error<Any>(Exception("Something went wrong!"))
    }

    protected fun convertErrorBody(error: HttpException): String {

        val reader: BufferedReader
        val sb = StringBuilder()

        val inputStream = error.response().errorBody()!!.byteStream()
        reader = BufferedReader(InputStreamReader(inputStream))
        try {
            var line = reader.readLine()
            while (line != null) {
                sb.append(line)
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return sb.toString()
    }
}
