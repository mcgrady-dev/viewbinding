package com.mcgrady.xlabs.viewbinding.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mcgrady.xlabs.viewbinding.delegate.FragmentInflateBindingDelegate
import com.mcgrady.xlabs.viewbinding.delegate.FragmentViewBindingDelegate
import kotlin.properties.ReadOnlyProperty

//inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(noinline initializer: (LayoutInflater) -> T) =
//    ActivityViewBindingPropertyDelegate(this, initializer)


inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
    noinline initializer: (LayoutInflater) -> T,
    setContentView: Boolean = true
) = lazy(LazyThreadSafetyMode.NONE) {
    initializer(layoutInflater).also { binding ->
        if (setContentView) setContentView(binding.root)
        if (binding is ViewDataBinding) binding.lifecycleOwner = this
    }
}

@JvmName("viewBinding")
inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline initializer: (View) -> T): ReadOnlyProperty<Fragment, T> =
    FragmentViewBindingDelegate(initializer)

@JvmName("binding")
inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline initializer: (LayoutInflater) -> T): ReadOnlyProperty<Fragment, T> =
    FragmentInflateBindingDelegate(initializer)


inline fun <reified T : ViewBinding> ViewGroup.inflate(noinline initializer: (LayoutInflater, ViewGroup?, Boolean) -> T) =
    initializer(LayoutInflater.from(context), this, true)

inline fun <reified T : ViewBinding> ViewGroup.binding(
    noinline initializer: (LayoutInflater, ViewGroup?, Boolean) -> T,
    attachToParent: Boolean = false
) = lazy(LazyThreadSafetyMode.NONE) {
    initializer(LayoutInflater.from(context), if (attachToParent) this else null, attachToParent)
}