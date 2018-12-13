package io.procrastination.foundation.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import timber.log.Timber

abstract class FoundationActivity<VB : ViewDataBinding, VM : FoundationViewModel<*>> : AppCompatActivity(), FoundationNavigator {

    private lateinit var mViewBinding: VB

    abstract fun getViewModel(): VM?

    fun getViewBinding(): VB = mViewBinding

    @LayoutRes abstract fun getLayoutResId(): Int

    abstract fun getBindingVariableId(): Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mViewBinding = DataBindingUtil.setContentView(this, getLayoutResId())

        getViewModel()?.let { vm ->

            getBindingVariableId()?.let { mViewBinding.setVariable(it, vm) }

            vm.setLifeCycleOwner(this)

            vm.isLoading.observe(this, Observer { /*TODO: Do something*/ })
        }
    }

    /* FoundationNavigator
     **********************************************************************************************/
    override fun onBack() {
        onBackPressed()
    }

    override fun hideKeyboard() {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            return
        }

        if (imm.isActive) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
        }
    }

    override fun handleError(error: Throwable) {
        Timber.w(error)
    }
}