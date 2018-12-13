package io.procrastination.foundation.view

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.procrastination.foundation.domain.usecases.UseCaseExecutor
import io.reactivex.disposables.CompositeDisposable

abstract class FoundationViewModel<N : FoundationNavigator> : ViewModel(), DefaultLifecycleObserver, UseCaseExecutor {

    lateinit var mNavigator: N
    private var mCompositeDisposable: CompositeDisposable? = null

    override val navigator: FoundationNavigator
        get() = mNavigator

    override val usecaseContainer: CompositeDisposable
        get() {
            if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed)
                mCompositeDisposable = CompositeDisposable()

            return mCompositeDisposable!!
        }

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: MutableLiveData<Boolean>
        get() = _isLoading

    override fun onCreate(owner: LifecycleOwner) {
        isLoading.postValue(false)
    }

    fun setLifeCycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    @Suppress("unused") fun setNavigator(navigator: N) {
        mNavigator = navigator
    }
}