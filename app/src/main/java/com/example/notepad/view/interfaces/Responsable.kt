package com.example.notepad.view.interfaces

import com.example.notepad.model.data.Note

interface Responsable {
    fun onResponse(list: List<Note>)
}