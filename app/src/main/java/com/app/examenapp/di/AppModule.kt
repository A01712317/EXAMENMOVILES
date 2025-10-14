package com.app.examenapp.di

import android.content.Context
import com.app.examenapp.data.local.preferences.PaisPreferences
import com.app.examenapp.data.remote.api.PaisesApi
import com.app.examenapp.data.repository.PaisRepositoryImpl
import com.app.examenapp.domain.repository.PaisRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Proporciona la instancia de Retrofit con la URL base de Rest Countries
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // Proporciona la instancia de Gson
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    // Proporciona la implementación de la API (PaisesApi)
    @Provides
    @Singleton
    fun providePaisesApi(retrofit: Retrofit): PaisesApi = retrofit.create(PaisesApi::class.java)

    // Proporciona la clase de preferencias para manejar el caché local
    @Provides
    @Singleton
    fun providePaisPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): PaisPreferences = PaisPreferences(context, gson)

    // Proporciona el binding de la interfaz PaisRepository a su implementación concreta
    @Provides
    @Singleton
    fun providePaisRepository(
        api: PaisesApi,
        preferences: PaisPreferences,
    ): PaisRepository = PaisRepositoryImpl(api, preferences)
}
