package com.example.nipashe.hilt

import com.example.nipashe.api.NewsApi
import com.example.nipashe.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)  //creates obj once when the app is started
@Module
object HiltModules {
    //things to inject
    @Singleton
    @Provides
    fun getApiInterface(): NewsApi {
        return return Retrofit.Builder()
            .baseUrl(Constants.base_url)
            .addConverterFactory(GsonConverterFactory.create()) // we need to add converter factory to  convert JSON object to Java object
            .build()
            .create(NewsApi::class.java)
    }
}