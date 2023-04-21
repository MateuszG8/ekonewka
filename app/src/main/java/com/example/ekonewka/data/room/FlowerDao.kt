package com.example.ekonewka.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ekonewka.data.models.Flower
import kotlinx.coroutines.flow.Flow


@Dao
interface FlowerDao {

    @Insert
    suspend fun insertFlower(flower: Flower)

    @Update
    suspend fun updateFlower(flower: Flower)

    @Delete
    suspend fun deleteFlower(flower: List<Flower>)

    @Query("SELECT * FROM flower_table ORDER BY 2")
    fun getAllFlowers(): Flow<List<Flower>>

    @Query("SELECT * FROM flower_table WHERE water is '1' ORDER BY water_schedule")
    fun getAllWater(): Flow<List<Flower>>

    @Query("SELECT * FROM flower_table WHERE sprinkle is '1' ORDER BY sprinkle_schedule")
    fun getAllSprinkle(): Flow<List<Flower>>

    @Query("SELECT * FROM flower_table WHERE fertilize is '1' ORDER BY fertilize_schedule")
    fun getAllFertilize(): Flow<List<Flower>>
}