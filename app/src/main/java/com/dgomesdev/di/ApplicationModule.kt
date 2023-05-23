package com.dgomesdev.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dgomesdev.data.di.DataModule
import com.dgomesdev.local.di.LocalModule
import com.dgomesdev.remote.di.NetworkModule
import com.dgomesdev.remote.di.RemoteModule
import com.dgomesdev.remote.di.ServiceModule

@Module(
    includes = [
        DataModule::class,
        LocalModule::class,
        RemoteModule::class,
        NetworkModule::class,
        ServiceModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}