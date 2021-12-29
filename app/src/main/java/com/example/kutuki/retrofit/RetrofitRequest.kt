package com.example.kutuki.retrofit

import com.example.kutuki.utils.Constants.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitRequest {

    //http://www.mocky.io/v2/5e2bebd23100007a00267e51
    //http://www.mocky.io/v2/5e2beb5a3100006600267e4e

    @Singleton
    @Provides
    fun getClientBuilder(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun getRetrofit(okHttpClient:OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }


    @Singleton
    @Provides
    fun getRequest(retrofit:Retrofit): ApiRequests = retrofit.create(ApiRequests::class.java)

}