package com.mcgrady.libs.viewbinding.delegate

import android.os.Looper
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.mcgrady.libs.viewbinding.extensions.observerWhenDestroyed
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val initializer: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (_value == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw IllegalThreadStateException("This cannot be called from other threads. It should be on the main thread only.")
            }
            _value = try {
                initializer(thisRef.requireView()).also { binding ->

                    if (binding is ViewDataBinding) binding.setLifecycleOwner(thisRef.viewLifecycleOwner)
                }
            } catch (e: IllegalStateException) {
                throw IllegalStateException("The property of ${property.name} has been destroyed.")
            }

            // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
            if (thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                thisRef.viewLifecycleOwner.lifecycle.observerWhenDestroyed {
                    _value = null
                }
            }
        }

        return _value!!
    }
}
