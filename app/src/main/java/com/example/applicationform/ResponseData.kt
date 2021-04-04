package com.example.applicationform

import UiData
import android.app.Activity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseData {

    private lateinit var listener: OnResponseCompleted
  fun getUserResponse(call: Call<UiData>)
  {
      call.enqueue(object: Callback<UiData>
      {
          override fun onResponse(call: Call<UiData>, response: Response<UiData>) {
              listener.onResponseCompleted(response.body(),true)
          }

          override fun onFailure(call: Call<UiData>, t: Throwable) {
              listener.onResponseCompleted(null,false)
          }

      })
  }

    fun getUIdata(listener: OnResponseCompleted){
        this.listener = listener
        getUserResponse(RetrofitClient.instance.fetchCustomUI())
    }
}