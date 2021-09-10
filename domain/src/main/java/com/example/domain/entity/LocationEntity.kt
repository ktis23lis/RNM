package com.example.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val locationId: Int,
    @ColumnInfo(name = "name")
    val locationName: String,
    @ColumnInfo(name = "type")
    val locationType: String,
    @ColumnInfo(name = "dimension")
    val locationDimension: String,
    @ColumnInfo(name = "url")
    val locationUrl: String
)
