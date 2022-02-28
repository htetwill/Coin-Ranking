package com.htetwill.coinranking.di

import com.htetwill.coinranking.common.activities.MainActivity
import com.htetwill.coinranking.di.annotation.Activity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @Activity
    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun mainActivity(): MainActivity
}