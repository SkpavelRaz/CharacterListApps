package com.example.task2_characterlist.networking

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {
    companion object{
        private val converterFactory = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        fun  buildApi(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(converterFactory)
                .build()
        }
    }

}