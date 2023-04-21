package com.example.ekonewka.data.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "flower_table")
data class Flower(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    var name: String,
    var water: Boolean,
    var sprinkle: Boolean,
    var fertilize: Boolean,
    var water_fp: Long,
    var sprinkle_fp: Long,
    var fertilize_fp: Long,
    var water_schedule: Long,
    var sprinkle_schedule: Long,
    var fertilize_schedule: Long,
    //var image: Bitmap
)
