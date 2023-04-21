package com.example.ekonewka.data.room

import android.content.Context
import androidx.room.Room


object DatabaseInstance {
    private var instance:FlowerDatabase? = null

    fun getInstance(context: Context): FlowerDatabase {
        if(instance == null)
            synchronized(FlowerDatabase::class.java){
                instance=roomBuild(context)
            }
        return instance!!
    }

    private fun roomBuild(context: Context): FlowerDatabase =
        Room.databaseBuilder(context,
        FlowerDatabase::class.java,
        "flower_database")
            .fallbackToDestructiveMigration()
            .build()
}