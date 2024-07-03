package com.example.fetch.api

import com.example.fetch.data.ItemAPIServices
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.fetch.utils.BASE_URL
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val URL = BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: ItemAPIServices by lazy {
        ApiModule.retrofit.create(ItemAPIServices::class.java)
    }
}
