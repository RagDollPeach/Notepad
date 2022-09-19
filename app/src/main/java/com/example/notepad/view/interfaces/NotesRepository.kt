package com.example.notepad.view.interfaces

import com.example.notepad.model.data.Note


interface NotesRepository {

    fun getAllNotes(callBack: Responsable)
    fun insertNote(note: Note)
    fun update(note: Note)
    fun deleteNote(title: String)
}