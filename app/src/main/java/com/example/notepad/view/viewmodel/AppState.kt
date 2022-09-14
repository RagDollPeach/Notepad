package com.example.notepad.view.viewmodel

import com.example.notepad.model.data.Note

sealed class AppState {
    data class Success(val noteData: List<Note>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}