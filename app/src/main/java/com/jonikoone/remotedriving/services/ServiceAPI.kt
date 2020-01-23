package com.jonikoone.remotedriving.services

import com.jonikoone.remotedriving.data.Offset
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceAPI {
    @POST("/api")
    fun sendCommand(@Query("message") msg: String): Call<String>

    @POST("/move")
    fun sendPosition(@Body offset: Offset): Call<String>

}