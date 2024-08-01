package com.example.yomikaze_app_kotlin.Core.Module

import android.app.Application
import android.content.Context
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.yomikaze_app_kotlin.Core.AppPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


enum class API(private val url: String) {
    TEST("https://jsonplaceholder.typicode.com/"),
    PRODUCTION("https://api.yomikaze.org/"),
    IMAGE("https://i.yomikaze.org/"),
    COVER_IMAGE("https://i.yomikaze.org");

    //toString
    override fun toString(): String {
        return url
    }
}

object APIConfig {
    var retrofitAPIURL: API = API.PRODUCTION
    var imageAPIURL: API = API.COVER_IMAGE
    var imageProductionAPIURL: API = API.IMAGE
}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageRetrofit

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
    @MainRetrofit
    @Provides
    @Singleton
    fun provideMainRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConfig.retrofitAPIURL.toString()) // Change this to your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * TODO: Provide the ImageRetrofit
     */
    @ImageRetrofit
    @Provides
    @Singleton
    fun provideImageRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConfig.imageProductionAPIURL.toString()) // Change this to your base URL
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
     * Todo: provide downloadWorker
     */
    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }


}