package com.example.yomikaze_app_kotlin.Core

import android.app.Application
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.Repository.AuthRepositoryImpl
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import com.example.yomikaze_app_kotlin.Domain.UseCase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/") // Change this to your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAppPreference(application: Application): AppPreference {
        return AppPreference(application)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        appPreference: AppPreference
    ): AuthRepository {
        return AuthRepositoryImpl(api, appPreference)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}