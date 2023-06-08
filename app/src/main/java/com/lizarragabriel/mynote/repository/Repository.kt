package com.lizarragabriel.mynote.repository

import com.lizarragabriel.mynote.room.dao.NoteDao
import com.lizarragabriel.mynote.room.entity.Note
import javax.inject.Inject

class Repository @Inject constructor(private val myDao: NoteDao) {

    fun mAddNote(note: Note) = myDao.mAddNote(note)

    fun mNote() = myDao.mNote()

    fun mUpdate(note: Note) = myDao.mUpdateNote(note)

    fun mDeleteNote(note: Note) = myDao.mDeleteNote(note)

    fun mDeleteCompleted() = myDao.mDeleteCompleted()

    fun mDeleteAll() = myDao.mDeleteAll()

}