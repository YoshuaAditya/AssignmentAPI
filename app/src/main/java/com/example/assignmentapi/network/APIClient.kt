package com.example.assignmentapi.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/iranjith4/ad-assignment/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}