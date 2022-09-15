package com.example.notepad.model

import com.example.notepad.model.data.Note


interface NotesRepository {

    fun getAllNotes(): List<Note>
    fun getNote(title: String): Note
    fun insertNote(note: Note)
}