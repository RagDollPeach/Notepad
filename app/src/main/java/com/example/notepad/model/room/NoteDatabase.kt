package com.example.notepad.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notepad.view.interfaces.NoteDAO

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDAO
}