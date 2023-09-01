package com.mcgrady.xproject.viewbinding.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mcgrady.xproject.viewbinding.R
import com.mcgrady.xproject.viewbinding.delegate.FragmentBindingDelegate
import com.mcgrady.xproject.viewbinding.delegate.FragmentInflateBindingDelegate

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(bind: (View) -> VB): VB =
    getTag(R.id.tag_view_binding) as? VB ?: bind(this).also { setTag(R.id.tag_view_binding, it) }

fun <VB : ViewBinding> ComponentActivity.viewBinding(
    inflate: (LayoutInflater) -> VB,
    setContentView: Boolean = true
) = lazy(
    LazyThreadSafetyMode.NONE
) {
    inflate(layoutInflater).also { binding ->
        if (setContentView) setContentView(binding.root)
        if (binding is ViewDataBinding) binding.lifecycleOwner = this
    }
}

fun <VB : ViewBinding> Fragment.viewBinding(bind: (View) -> VB) = FragmentBindingDelegate(bind)

fun <VB : ViewBinding> Fragment.viewBinding(inflate: (LayoutInflater) -> VB) = FragmentInflateBindingDelegate(inflate)