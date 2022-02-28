package com.htetwill.coinranking.di

import com.htetwill.coinranking.common.fragment.CustomFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRecyclerViewFragment(): CustomFragment

}
