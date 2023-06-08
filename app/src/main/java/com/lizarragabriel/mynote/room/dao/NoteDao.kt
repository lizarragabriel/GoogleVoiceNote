package com.lizarragabriel.mynote.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lizarragabriel.mynote.room.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY isComplete ASC, time DESC")
    fun mNote(): LiveData<List<Note>>

    @Insert
    fun mAddNote(alumno: Note)

    @Update
    fun mUpdateNote(note: Note)

    @Query("DELETE FROM notes")
    fun mDeleteAll()

    @Query("DELETE FROM notes WHERE isComplete = 1")
    fun mDeleteCompleted()

    @Delete
    fun mDeleteNote(note: Note)
}