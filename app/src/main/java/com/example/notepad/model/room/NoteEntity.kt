package com.example.notepad.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_entity")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val text: String,
    val date: String
)