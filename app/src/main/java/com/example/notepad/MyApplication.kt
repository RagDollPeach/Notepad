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
                        .build()
            }
            return noteDatabase!!
        }

//        private val migration = object : Migration(1,2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE note_entity ADD COLUMN color  DEFAULT 0  NOT NULL ")
//            }
//        }
    }
}