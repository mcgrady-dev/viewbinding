package com.mcgrady.xproject.viewbinding.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle

internal class ActivityLifecycleCallbacksImpl(private var destroyed: (() -> Unit)? = null) :
    Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        destroyed?.invoke()
        activity.application.unregisterActivityLifecycleCallbacks(this)

        destroyed = null
    }
}