package com.example.android.di

import com.example.android.recyclerview.RecyclerViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRecyclerViewFragment(): RecyclerViewFragment

}
