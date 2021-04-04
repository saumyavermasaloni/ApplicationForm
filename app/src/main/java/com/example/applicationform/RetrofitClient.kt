package com.example.applicationform

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
   //  private const val BASE_URL = "https://demo.ezetap.com/mobileapps/"
   private const val BASE_URL = "https://demo1286554.mockable.io/"

  val instance : NetworkService by lazy {
     val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
     retrofit.create(NetworkService::class.java)
  }
}