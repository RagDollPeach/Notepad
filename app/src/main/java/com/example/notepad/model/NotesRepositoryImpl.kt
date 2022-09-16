package com.example.notepad.model

import com.example.notepad.MyApplication
import com.example.notepad.model.data.Note
import com.example.notepad.model.room.NoteEntity
import com.example.notepad.view.interfaces.NotesRepository

class NotesRepositoryImpl: NotesRepository {

    override fun getAllNotes(): List<Note>{
        return  converterEntityToNote(MyApplication.getNoteDatabase().noteDao().getAllNotes())
    }

    override fun insertNote(note: Note) {
        MyApplication.getNoteDatabase().noteDao().insert(converterWeatherToEntity(note))
    }

    override fun deleteNote(title: String) {
        MyApplication.getNoteDatabase().noteDao().deleteNoteBYTitle(title)
    }

    override fun getNote(title:String): Note {
        return Note("title", "text", "date")
    }

    private fun converterEntityToNote(entityList: List<NoteEntity>): List<Note> {
        return entityList.map {
            Note(it.title,it.text,it.date)
        }
    }

    private fun converterWeatherToEntity(note: Note): NoteEntity {
        return NoteEntity(
            0,
            note.title,
            note.text,
            note.date
        )
    }
}