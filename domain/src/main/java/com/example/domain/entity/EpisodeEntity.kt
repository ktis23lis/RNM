package com.example.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val episodeId: Int,
    @ColumnInfo(name = "name")
    val episodeName: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "airDate")
    val episodeAirFate: String
)

