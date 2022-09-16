package com.example.notepad.view.interfaces

import com.example.notepad.model.data.Note


interface NotesRepository {

    fun getAllNotes(): List<Note>
    fun getNote(title: String): Note
    fun insertNote(note: Note)
    fun deleteNote(title: String)
}