package io.procrastination.foundation.view.popups

import android.widget.PopupWindow

interface PopupHandler {

    fun registerPopup(popup: PopupWindow)

    fun unregisterPopup(popup: PopupWindow)

    fun clearPopups()
}

class PopupHandlerImpl : PopupHandler {

    private var popups: MutableList<PopupWindow> = emptyList<PopupWindow>().toMutableList()

    override fun registerPopup(popup: PopupWindow) {
        popups.add(popup)
    }

    override fun unregisterPopup(popup: PopupWindow) {
        popups.remove(popup)
    }

    override fun clearPopups() {
        popups.forEach { if (it.isShowing) it.dismiss() }
        popups.clear()
    }
}