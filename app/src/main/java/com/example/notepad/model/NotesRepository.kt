package com.example.notepad.model

import com.example.notepad.model.data.Note
import com.example.notepad.view.interfaces.Responsable


interface NotesRepository {

    fun getAllNotes(callBack: Responsable)
    fun insertNote(note: Note)
    fun updateByTitle(note: Note)
    fun updateByText(note: Note)
    fun deleteNote(title: String)
}