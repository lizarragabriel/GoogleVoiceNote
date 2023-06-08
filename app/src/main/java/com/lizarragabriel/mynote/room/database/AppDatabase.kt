package com.lizarragabriel.mynote.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lizarragabriel.mynote.room.dao.NoteDao
import com.lizarragabriel.mynote.room.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun myDao(): NoteDao
}