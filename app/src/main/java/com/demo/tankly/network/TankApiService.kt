package com.demo.tankly.network

import com.demo.tankly.data.model.Tank
import retrofit2.http.GET
import retrofit2.http.Path

interface TankApiService {
    @GET("api/tanks")
    suspend fun getTanks(): List<Tank>

    @GET("api/tanks/{id}")
    suspend fun getTankById(@Path("id") id: Int): Tank
}
