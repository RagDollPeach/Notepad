package com.example.notepad.model

import com.example.notepad.model.data.Note

class NotesRepositoryImpl: NotesRepository {

    override fun getNotes(): List<Note> {
        return listOf()
    }

    override fun getNote(): Note {
        return Note("Super Title","test","01.05.2022")
    }
}