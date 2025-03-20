package com.demo.tankly.ui.tankdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.tankly.data.model.Tank
import com.demo.tankly.data.repository.TankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TankDetailViewModel @Inject constructor(
    private val repository: TankRepository
) : ViewModel() {

    private val _tank = MutableLiveData<Tank?>()
    val tank: LiveData<Tank?> = _tank

    private fun fetchTankById(tankId: Int) {
        viewModelScope.launch {
            try {
                val fetchedTank = repository.getTankById(tankId)
                if (fetchedTank != null) {
                    _tank.value = fetchedTank
                } else {
                    Log.d("TankDetailViewModel", "Tank not found, ID: $tankId")
                    _tank.value = null
                }
            } catch (e: Exception) {
                Log.e("TankDetailViewModel", "Error fetching tank by ID", e)
                _tank.value = null
            }
        }
    }

    fun getTank(tankId: Int) {
        fetchTankById(tankId)
    }
}