package com.demo.tankly.ui.tanklist

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
class TankListViewModel @Inject constructor(
    private val repository: TankRepository
) : ViewModel() {

    private val _tankList = MutableLiveData<List<Tank>>()
    val tankList: LiveData<List<Tank>> = _tankList

    init {
        fetchTanks()
    }

    private fun fetchTanks() {
        viewModelScope.launch {
            try {
                val tanks = repository.getTanks()
                if (tanks.isNotEmpty()) {
                    _tankList.value = tanks
                } else {
                    Log.d("TankListViewModel", "No tanks found.")
                    _tankList.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("TankListViewModel", "Error fetching tanks", e)
                _tankList.value = emptyList()
            }
        }
    }

    fun refreshTanks() {
        fetchTanks()
    }
}
