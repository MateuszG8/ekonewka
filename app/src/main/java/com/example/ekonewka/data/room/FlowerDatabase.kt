package com.example.ekonewka.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ekonewka.data.models.Flower


@Database (entities = [Flower::class], version = 2, exportSchema = false)

abstract class FlowerDatabase : RoomDatabase() {
    abstract fun flowerDAO(): FlowerDao
}