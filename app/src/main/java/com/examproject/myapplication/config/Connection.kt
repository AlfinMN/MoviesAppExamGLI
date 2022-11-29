package com.examproject.myapplication.config

import com.examproject.myapplication.utils.BASE_URL_API
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Alfin Muhammad Nurdin on 28/11/22.
 * alfinmuhammadnurdin@gmail.com
 */

class Connection {
    companion object {
        var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        fun urlCon(): Retrofit {
            val connection = Retrofit
                .Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient!!)
                .build()
            return connection
        }
    }
}