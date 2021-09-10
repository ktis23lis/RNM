package com.example.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "personage")
data class PersonageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val personageId: Int,
    @ColumnInfo(name = "image")
    val personageImage: String,
    @ColumnInfo(name = "name")
    val personageName: String,
    @ColumnInfo(name = "species")
    val personageSpecies: String,
    @ColumnInfo(name = "status")
    val personageStatus: String,
    @ColumnInfo(name = "gender")
    val personageGender: String,
    @ColumnInfo(name = "origin")
    val personageOriginName : String,
    @ColumnInfo(name = "originUrl")
    val personagePersonageUrl : String,
    @ColumnInfo(name = "location")
    val personageLocation : String,
    @ColumnInfo(name = "locationUrl")
    val personageLocationUrl : String
)