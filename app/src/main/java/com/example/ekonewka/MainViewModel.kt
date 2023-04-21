package com.example.ekonewka

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ekonewka.data.FlowerRepository
import com.example.ekonewka.data.models.Flower
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application): AndroidViewModel(app) {
    var isNavMainVisible = true
    private val repo = FlowerRepository(app.applicationContext)
    private var selectedFlower: Flower? = null

    fun insertFlower(flower: Flower) =
        CoroutineScope(Dispatchers.IO).launch { repo.insertFlower(flower) }

    fun updateFlower(flower: Flower) =
        CoroutineScope(Dispatchers.IO).launch { repo.updateFlower(flower) }

    fun deleteFlower(flower: List<Flower>) =
        CoroutineScope(Dispatchers.IO).launch { repo.deleteFlower(flower) }

    fun getAllFlowers() =
        repo.getAllFlowers().asLiveData(viewModelScope.coroutineContext)

    fun getAllWater() =
        repo.getAllWater().asLiveData(viewModelScope.coroutineContext)

    fun getAllSprinkle() =
        repo.getAllSprinkle().asLiveData(viewModelScope.coroutineContext)

    fun getAllFertilize() =
        repo.getAllFertilize().asLiveData(viewModelScope.coroutineContext)

    fun selectFlower(flower: Flower) {
        selectedFlower =  flower
    }

    fun unselectFlower(){
        selectedFlower = null
    }

    fun getSelectedFlower() = selectedFlower

}
