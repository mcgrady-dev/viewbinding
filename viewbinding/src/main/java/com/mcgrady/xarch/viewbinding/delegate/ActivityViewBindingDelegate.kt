package com.mcgrady.xarch.viewbinding.delegate

import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class ActivityViewBindingDelegate<T: ViewBinding>(
    private val activity: AppCompatActivity,
    private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<AppCompatActivity, T>, DefaultLifecycleObserver {

    private var _value: T? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (_value == null) {
            _value = initializer(activity.layoutInflater)
        }
        activity.setContentView(_value?.root!!)
        activity.lifecycle.removeObserver(this)
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (_value == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw IllegalThreadStateException("This cannot be called from other threads. It should be on the main thread only.")
            }

            initializer(thisRef.layoutInflater).also {
                if (thisRef.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    thisRef.lifecycle.addObserver(this)
                }
                _value = it
            }
        }

        return _value!!
    }


}