package com.lizarragabriel.mynote.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lizarragabriel.mynote.repository.Repository
import com.lizarragabriel.mynote.room.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val notes: LiveData<List<Note>>
        get() = repository.mNote()

    fun mTextVoice(note: String) {
        try {
            if(note.isEmpty()) return
            val date = Date()
            val mNewNote = Note(null, note, date.time, mFormatDate(date), false)
            mAddNote(mNewNote)
        }catch (e: Exception) {
            println(e.message)
        }
    }

    fun mAddNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mAddNote(note)
        }
    }

    fun mUpdate(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            note.isComplete = !note.isComplete
            repository.mUpdate(note)
        }
    }

    fun mDeleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mDeleteNote(note)
        }
    }

    fun mDeleteCompleted() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mDeleteCompleted()
        }
    }

    fun mDeleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mDeleteAll()
        }
    }

    private fun mFormatDate(date: Date): String {
        return SimpleDateFormat("dd MMMM yyyy HH:mm").format(date)
    }
}