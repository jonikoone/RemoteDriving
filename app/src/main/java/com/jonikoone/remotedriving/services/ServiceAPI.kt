package com.jonikoone.remotedriving.services

import com.jonikoone.remotedriving.data.Offset
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService

interface ServiceAPI : RetrofitService {
    @POST("/api")
    fun sendCommand(@Query("message") msg: String): Call<String>

    @POST("/move")
    fun sendPosition(@Body offset: Offset): Call<String>

    @POST("/clickLMB")
    fun sendClickMouseLeftButton(): Call<Unit>

    @POST("/clickRMB")
    fun sendClickMouseRightButton(): Call<Unit>

    @POST("/press")
    fun sendPresskMouseLeftButton(): Call<Unit>

    @POST("/release")
    fun sendReleaseMouseLeftButton(): Call<Unit>

    @POST("/scrollwheel")
    fun sendScrollWheel(@Body offset: Offset): Call<Unit>

}