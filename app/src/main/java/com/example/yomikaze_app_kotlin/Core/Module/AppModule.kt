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


enum class API(private val url: String) {
    TEST("https://jsonplaceholder.typicode.com/"),
    PRODUCTION("https://api.yomikaze.org/"),
    COVER_IMAGE("https://i.yomikaze.org");

    //toString
    override fun toString(): String {
        return url
    }
}

object APIConfig {
    var retrofitAPIURL: API = API.PRODUCTION
    var imageAPIURL: API = API.COVER_IMAGE
}

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

    @Provides
    @Singleton
    fun provideApiUrl(): API {
        return API.PRODUCTION
    }

    /**
     * Todo: Provide the Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(apiUrl: API): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConfig.retrofitAPIURL.toString()) // Change this to your base URL
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

}