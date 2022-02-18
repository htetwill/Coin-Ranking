package com.example.android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.di.annotation.ViewModelKey
import com.example.android.recyclerview.factory.ViewModelFactory
import com.example.android.recyclerview.viewmodel.V2RecyclerViewFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(V2RecyclerViewFragmentViewModel::class)
    abstract fun bindV2RecyclerViewFragmentViewModel(applicationViewModel: V2RecyclerViewFragmentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}