package com.example.notepad

import android.app.Application
import androidx.room.Room
import com.example.notepad.model.room.NoteDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }

    companion object {
        private var myApplication: MyApplication? = null
        fun getMyApp() = myApplication!!
        private var noteDatabase: NoteDatabase? = null

        fun getNoteDatabase(): NoteDatabase {
            if (noteDatabase == null) {
                noteDatabase =
                    Room.databaseBuilder(getMyApp(), NoteDatabase::class.java, "MyDatabase")
                        .allowMainThreadQueries()
                        .build()
            }
            return noteDatabase!!
        }
    }
}