package com.lizarragabriel.mynote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val time: Long,
    val date: String,
    var isComplete: Boolean = false)