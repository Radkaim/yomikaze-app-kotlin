package com.example.yomikaze_app_kotlin.Core.Module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

//    @Binds
//    abstract fun bindWorkerFactory(factory: HiltWorkerFactory): WorkerFactory

}
