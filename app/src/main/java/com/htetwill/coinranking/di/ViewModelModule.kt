package com.htetwill.coinranking.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htetwill.coinranking.common.viewmodel.CustomViewModel
import com.htetwill.coinranking.di.annotation.ViewModelKey
import com.htetwill.coinranking.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CustomViewModel::class)
    abstract fun bindV2RecyclerViewFragmentViewModel(applicationViewModel: CustomViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}