package io.procrastination.foundation.view

interface FoundationNavigator {

    fun handleError(error : Throwable)

    fun onBack()

    fun hideKeyboard()
}