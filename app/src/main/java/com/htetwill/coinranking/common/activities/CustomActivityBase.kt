package com.htetwill.coinranking.common.activities

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Base launcher activity.
 */
open class CustomActivityBase : FragmentActivity(), HasAndroidInjector, LifecycleOwner {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}
