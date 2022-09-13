package com.example.notepad.model

import com.example.notepad.model.data.Note

interface NotesRepository {

    fun getNotes(): List<Note>
    fun getNote(): Note
}