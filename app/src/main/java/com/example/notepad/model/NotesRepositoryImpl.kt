package com.example.notepad.model

import com.example.notepad.model.data.Note

class NotesRepositoryImpl: NotesRepository {


    override fun getNotes(): List<Note> {
        return mutableListOf(
           Note("Super Title", "test", "01.05.2022"),
           Note("Super1 Title", "test1", "01.05.2022"),
           Note("Super2 Title", "test2", "01.05.2022"),
           Note("Super3 Title", "test3", "01.05.2022"),
           Note("Super4 Title", "test4", "01.05.2022"),
           Note("Super5 Title", "test5", "01.05.2022")
       )
    }

    override fun getNote(): Note {
        return Note("Super Title","test","01.05.2022")
    }
}