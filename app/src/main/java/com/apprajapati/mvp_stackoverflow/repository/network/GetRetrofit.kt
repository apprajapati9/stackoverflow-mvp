package com.apprajapati.mvp_stackoverflow.repository.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetRetrofit {

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //client..
    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    //retrofit
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://api.stackexchange.com/").client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    //api interface
    val stackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }

}