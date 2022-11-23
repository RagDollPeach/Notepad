package com.example.notepad.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDAO {

    @Insert(onConflict = REPLACE)
    fun insert(noteEntity: NoteEntity)

    @Query("UPDATE note_entity SET text=:text, date=:date, color=:color WHERE title=:title" )
    fun updateNoteBYTitle(title: String, text: String, date: String, color: String)

    @Query("UPDATE note_entity SET title=:title, date=:date, color=:color WHERE text=:text" )
    fun updateNoteBYText(title: String, text: String, date: String, color: String)

    @Query("SELECT * FROM note_entity")
    fun getAllNotes(): List<NoteEntity>

    @Query("DELETE FROM note_entity WHERE title=:title")
    fun deleteNoteBYTitle(title: String)
}