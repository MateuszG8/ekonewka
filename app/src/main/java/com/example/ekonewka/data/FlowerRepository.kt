package com.example.ekonewka.data

import android.content.Context
import com.example.ekonewka.data.models.Flower
import com.example.ekonewka.data.room.DatabaseInstance

class FlowerRepository(context: Context) {
    private val flowerDao = DatabaseInstance.getInstance(context).flowerDAO()

    suspend fun insertFlower(flower: Flower){
        flowerDao.insertFlower(flower)
    }

    suspend fun updateFlower(flower: Flower){
        flowerDao.updateFlower(flower)
    }

    suspend fun deleteFlower(flower: List<Flower>){
        flowerDao.deleteFlower(flower)
    }

    fun getAllFlowers() = flowerDao.getAllFlowers()

    fun getAllWater() = flowerDao.getAllWater()
    fun getAllSprinkle() = flowerDao.getAllSprinkle()
    fun getAllFertilize() = flowerDao.getAllFertilize()

}