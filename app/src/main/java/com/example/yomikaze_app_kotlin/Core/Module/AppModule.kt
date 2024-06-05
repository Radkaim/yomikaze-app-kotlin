package com.example.yomikaze_app_kotlin.Core.Module

import android.app.Application
import com.example.yomikaze_app_kotlin.Core.AppPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Todo: Provide the AppPreference
     */
    @Provides
    @Singleton
    fun provideAppPreference(application: Application): AppPreference {
        return AppPreference(application)
    }

    /**
     * Todo: Provide the Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Change this to your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}