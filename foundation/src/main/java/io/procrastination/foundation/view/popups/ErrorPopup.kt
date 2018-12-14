package io.procrastination.foundation.view.popups

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import io.procrastination.foundation.R

class ErrorPopup(activity: Activity) : FoundationPopupWindow(activity) {

    private lateinit var txtErrorMessage: TextView

    override val layoutResourceId: Int
        get() = R.layout.popup_error

    override fun bindViews(contentView: View) {
        txtErrorMessage = contentView.findViewById(R.id.txt_error_message)
    }

    override fun setupEventListeners() {
        rootView?.let { v -> v.setOnClickListener { dismiss() } }
    }

    fun setError(error: String): ErrorPopup {
        txtErrorMessage.text = error
        return this
    }

    @Suppress("unused") fun setError(@StringRes error: Int): ErrorPopup {
        txtErrorMessage.text = getString(error)
        return this
    }
}