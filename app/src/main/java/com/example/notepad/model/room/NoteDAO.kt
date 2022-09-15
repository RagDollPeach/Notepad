package com.example.notepad.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDAO {

    @Insert(onConflict = REPLACE)
    fun insert(noteEntity: NoteEntity)

    @Query("SELECT * FROM note_entity")
    fun getAllNotes(): List<NoteEntity>

//    @Query("SELECT title,text,date FROM note_entity WHERE title=:title")
//    fun getNoteByTitle(title: String): List<NoteEntity>

    @Query("DELETE FROM note_entity WHERE title=:title")
    fun deleteNoteBYTitle(title: String)
}