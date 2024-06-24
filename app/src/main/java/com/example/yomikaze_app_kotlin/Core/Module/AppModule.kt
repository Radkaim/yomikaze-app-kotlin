package com.example.yomikaze_app_kotlin.Core.Module

import android.app.Application
import androidx.navigation.NavController
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
    private const val ApiUrlTest = "https://yomikaze.org/api/"
    private const val ApiUrl = "https://yomikaze.org/api/"
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiUrl) // Change this to your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Todo: Provide the NavigationController
     */
    @Provides
    @Singleton
    fun provideNavController(application: Application): NavController {
        return NavController(application)
    }

    /**
     * Todo: Provide the RoomDatabase
     */


}