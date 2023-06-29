package com.example.assignmentapi.network;

import com.example.assignmentapi.objects.Exclusion
import com.example.assignmentapi.objects.Facility
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("facilities")
    fun getFacilities(): Call<MutableList<Facility>>
    @GET("exclusions")
    fun getExclusions(): Call<MutableList<MutableList<Exclusion>>>
}