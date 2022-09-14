package com.example.notepad.view.interfaces

import com.example.notepad.model.data.Note

fun interface OnItemClick {
    fun onItemClick(note: Note)
}