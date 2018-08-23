package io.procrastination.foundation.domain.usecases

import android.arch.lifecycle.MutableLiveData
import io.procrastination.foundation.view.FoundationNavigator
import io.procrastination.foundation.view.containIn
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import timber.log.Timber

interface UseCaseExecutor {

    val isLoading : MutableLiveData<Boolean>
    val usecaseContainer : CompositeDisposable
    val navigator : FoundationNavigator

    /* Foreground
     ************************************************************************************************/
    /**
     * Executes the provided use case with the input parameter, with foreground feedback, i.e. loading.
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param onSuccess [Consumer] that specified the on success behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInForeground(useCase: ObservableUseCase<T, P>, input: P, onSuccess: Consumer<T>){
        execute(useCase, input, onSuccess = onSuccess, isForeground = true)
    }

    open fun buildStandardErrorHandler(isForeground: Boolean, navigator : FoundationNavigator) : Consumer<Throwable> {
        return Consumer {
            if(isForeground) isLoading.postValue(false)

            navigator.handleError(it)
            Timber.e(it)
        }
    }

    /**
     * Executes the provided use case with the input parameter, with foreground feedback, i.e. loading.
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param filter [Predicate] that filter out the result. The predicate should return true to continue, or false to abort.
     * @param onSuccess [Consumer] that specified the on success behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInForeground(useCase: ObservableUseCase<T, P>, input: P, filter: Predicate<T>, onSuccess: Consumer<T>){
        execute(useCase, input, filter = filter, onSuccess = onSuccess, isForeground = true)
    }

    /**
     * Executes the provided use case with the input parameter, with foreground feedback, i.e. loading.
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param filter [Predicate] that filter out the result. The predicate should return true to continue, or false to abort.
     * @param onSuccess [Consumer] that specified the on success behavior.
     * @param onError [Consumer] that specifies the on error behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInForeground(useCase: ObservableUseCase<T, P>, input: P, filter: Predicate<T>, onSuccess: Consumer<T>, onError: Consumer<Throwable>){
        execute(useCase, input, filter = filter, onSuccess = onSuccess, onError = onError, isForeground = true)
    }

    /**
     * Executes the provided use case with the input parameter, with foreground feedback, i.e. loading.
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param onSuccess [Consumer] that specifies the on success behavior.
     * @param onError [Consumer] that specifies the on error behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInForeground(useCase: ObservableUseCase<T, P>, input: P, onSuccess: Consumer<T>, onError: Consumer<Throwable>){
        execute(useCase, input, onSuccess = onSuccess, isForeground = true)
    }

    /* Background
     ************************************************************************************************/
    /**
     * Executes the provided use case with the input parameter in the background
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param onSuccess [Consumer] that specifies the on success behavior.
     * @param onError [Consumer] that specifies the on error behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInBackground(useCase: ObservableUseCase<T, P>, input: P, onSuccess: Consumer<T>, onError: Consumer<Throwable>){
        execute(useCase, input, onSuccess = onSuccess, onError = onError, isForeground = false)
    }

    /**
     * Executes the provided use case with the input parameter in the background
     * @param useCase The [ObservableUseCase] use-case to be executed.
     * @param input The input parameters
     * @param onSuccess [Consumer] that specifies the on success behavior.
     * @param T The [ObservableUseCase] output generic type.
     * @param P The [ObservableUseCase] input generic type.
     */
    fun <T, P> executeUseCaseInBackground(useCase: ObservableUseCase<T, P>, input: P, onSuccess: Consumer<T>){
        execute(useCase, input, onSuccess = onSuccess, isForeground = false)
    }

    /**
     * TODO document
     * @param useCase
     * @param input
     * @param filter
     * @param onSuccess
     * @param <T>
     * @param <P>
     */
    fun <T, P> executeUseCaseInBackground(useCase: ObservableUseCase<T, P>, input: P, filter: Predicate<T>, onSuccess: Consumer<T>) {
        execute(useCase, input, filter, onSuccess, isForeground = false)
    }

    private fun <T, P> execute(useCase : ObservableUseCase<T, P>,
                               input: P,
                               filter : Predicate<T> = Predicate { true },
                               onSuccess : Consumer<T>,
                               onError: Consumer<Throwable>? = null,
                               isForeground : Boolean) {

        if(isForeground) isLoading.postValue(true)

        useCase.execute(input, filter, onSuccess,
                onError?: buildStandardErrorHandler(isForeground, navigator),
                Action {
                    if(isForeground) isLoading.postValue(false)
                }).containIn(usecaseContainer)

    }
}
