package com.htetwill.coinranking.di

import android.app.Application
import com.htetwill.coinranking.common.CustomApp
import com.htetwill.coinranking.data.api.CustomApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (ApiModule::class),
        (ViewModelModule::class),
        (ActivityBindingModule::class),
        (AndroidInjectionModule::class)
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application) : AppComponent.Builder
        fun build():AppComponent
    }

    fun inject(customApi: CustomApi)
    fun inject(customApplication: CustomApp)
}