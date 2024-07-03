package com.example.fetch.data

import retrofit2.Call
import retrofit2.http.GET

interface ItemAPIServices {
    @GET("/hiring.json")
    fun getItemsList(): Call<List<ItemData>>
}
