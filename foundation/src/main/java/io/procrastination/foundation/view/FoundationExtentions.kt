package io.procrastination.foundation.view

import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Any?.notNull(f: () -> Unit) {
    if (this != null) {
        f()
    }
}

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}


fun Context.nullOrEmpty(string : String?) : Boolean{
    return string == null || string.isEmpty()
}

/* Rx Extensions
 ***************************************************************************************************/
fun Disposable.containIn(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Exception.toObservable(): Observable<T> {
    return Observable.error(this)
}