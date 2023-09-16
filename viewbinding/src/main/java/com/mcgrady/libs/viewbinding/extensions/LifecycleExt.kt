package com.mcgrady.libs.viewbinding.extensions


import androidx.lifecycle.Lifecycle
import com.mcgrady.libs.viewbinding.lifecycle.ViewBindingLifecycleObserver

internal fun Lifecycle.observerWhenCreated(create: () -> Unit) {
    addObserver(ViewBindingLifecycleObserver(lifecycle = this, create = create))
}

internal fun Lifecycle.observerWhenDestroyed(destroyed: () -> Unit) {
    addObserver(ViewBindingLifecycleObserver(lifecycle = this, destroyed = destroyed))
}