package com.example.android.di

import com.example.android.di.annotation.Activity
import com.example.android.recyclerview.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @Activity
    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun mainActivity():MainActivity
}