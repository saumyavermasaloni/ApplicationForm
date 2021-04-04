package com.example.applicationform

import UiData
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    @GET("form")
    fun fetchCustomUI(): Call<UiData>
}