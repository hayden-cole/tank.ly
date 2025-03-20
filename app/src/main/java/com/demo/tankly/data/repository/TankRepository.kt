package com.demo.tankly.data.repository

import com.demo.tankly.data.model.Tank
import com.demo.tankly.network.TankApiService
import javax.inject.Inject

class TankRepository @Inject constructor(
    private val apiService: TankApiService
) {

    suspend fun getTanks(): List<Tank> {
        return apiService.getTanks()
    }

    suspend fun getTankById(id: Int): Tank? {
        return apiService.getTankById(id)
    }
}
