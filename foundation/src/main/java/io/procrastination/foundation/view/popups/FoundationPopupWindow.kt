package io.procrastination.foundation.view.popups

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow

abstract class FoundationPopupWindow
internal constructor(activity : Activity, width : Int = -1, height : Int = -1)  : PopupWindow() {

    private var context: Context
    protected var rootView: View? = null

    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    protected abstract fun bindViews(contentView: View)

    protected abstract fun setupEventListeners()

    init {
        isOutsideTouchable = true
        isFocusable = true


        val contentView = getDefaultPopupView(activity)
        this.contentView = contentView
        this.width = width
        this.height = height
        this.context = contentView.context
        this.rootView = contentView

        bindViews(contentView)
        setupEventListeners()

        rootView?.let {
            it.setOnKeyListener { _, _, _-> false }
        }
    }

    fun attachOnDismissListener(listener: PopupWindow.OnDismissListener) {
        setOnDismissListener(listener)
    }

    private fun getDefaultPopupView(activity: Activity): View {
        return activity.layoutInflater.inflate(layoutResourceId, null)
    }

    fun show(anchor: View) : PopupWindow{
        val location = IntArray(2)
        anchor.getLocationOnScreen(location)

        anchor.post { showAtLocation(anchor, Gravity.CENTER_VERTICAL, location[0], location[1] - anchor.height / 2) }
        return this
    }

    fun show(anchor: View, location: IntArray) : PopupWindow{
        anchor.getLocationOnScreen(location)

        anchor.post { showAtLocation(anchor, Gravity.CENTER_VERTICAL, location[0], location[1]) }
        return this
    }

    protected fun getString(@StringRes id : Int) : String = context.getString(id)

    /**
     * If executed the handling of the onBack event reverts back to the
     * activity that called the popup window.
     */
    fun giveUpControl() {
        isFocusable = false
    }
}