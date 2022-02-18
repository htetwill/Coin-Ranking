package com.example.android.recyclerview

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.android.di.AppInjector
import com.example.android.di.DaggerAppComponent
import dagger.android.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomApp : Application(), HasAndroidInjector{
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}