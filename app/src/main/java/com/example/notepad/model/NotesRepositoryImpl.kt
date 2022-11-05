package com.example.notepad.model

import com.example.notepad.MyApplication
import com.example.notepad.model.data.Note
import com.example.notepad.model.room.NoteEntity
import com.example.notepad.view.interfaces.Responsable

class NotesRepositoryImpl : NotesRepository {

    override fun getAllNotes(callBack: Responsable) {
        Thread {
            callBack.onResponse(converterEntityToNote(MyApplication.getNoteDatabase().noteDao().getAllNotes()) as MutableList<Note>)
        }.start()
    }

    override fun insertNote(note: Note) {
        Thread {
            MyApplication.getNoteDatabase().noteDao().insert(converterWeatherToEntity(note))
        }.start()
    }

    override fun update(note: Note) {
        Thread {
            MyApplication.getNoteDatabase().noteDao().updateNoteBYTitle(note.title,note.text,note.date,note.color)
        }.start()
    }

    override fun deleteNote(title: String) {
        Thread {
            MyApplication.getNoteDatabase().noteDao().deleteNoteBYTitle(title)
        }.start()
    }

    private fun converterEntityToNote(entityList: List<NoteEntity>): List<Note> {
        return entityList.map {
            Note(it.title, it.text, it.date,it.color)
        }
    }

    private fun converterWeatherToEntity(note: Note): NoteEntity {
        return NoteEntity(
            0,
            note.title,
            note.text,
            note.date,
            note.color
        )
    }
}