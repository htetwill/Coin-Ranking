package com.example.android.common.activities

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Base launcher activity.
 */
open class SampleActivityBase : FragmentActivity(), HasAndroidInjector, LifecycleOwner {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}
