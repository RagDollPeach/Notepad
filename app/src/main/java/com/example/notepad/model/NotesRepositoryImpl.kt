package com.example.notepad.model

import com.example.notepad.MyApplication
import com.example.notepad.model.data.Note
import com.example.notepad.model.room.NoteEntity
import com.example.notepad.view.interfaces.NotesRepository
import com.example.notepad.view.interfaces.Responsable

class NotesRepositoryImpl : NotesRepository {

    override fun getAllNotes(callBack: Responsable) {
        Thread {
            callBack.onResponse(converterEntityToNote(MyApplication.getNoteDatabase().noteDao().getAllNotes()))
        }.start()
    }

    override fun insertNote(note: Note) {
        Thread {
            MyApplication.getNoteDatabase().noteDao().insert(converterWeatherToEntity(note))
        }.start()

    }

    override fun deleteNote(title: String) {
        Thread {
            MyApplication.getNoteDatabase().noteDao().deleteNoteBYTitle(title)
        }.start()

    }

    private fun converterEntityToNote(entityList: List<NoteEntity>): List<Note> {
        return entityList.map {
            Note(it.title, it.text, it.date)
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