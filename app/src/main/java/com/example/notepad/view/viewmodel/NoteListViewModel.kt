package com.example.notepad.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.view.interfaces.NotesRepository
import com.example.notepad.model.NotesRepositoryImpl
import com.example.notepad.model.data.Note

class NoteListViewModel(private val lifeData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel() {

    private val repository: NotesRepository = NotesRepositoryImpl()

    fun getLifeData(): MutableLiveData<AppState> {
        return lifeData
    }

    fun sendRequest() {
//        lifeData.value = AppState.Error(Exception())
        lifeData.value = AppState.Success(repository.getAllNotes())
    }

    fun insert(note: Note) {
        repository.insertNote(note)
    }

    fun delete(title: String) {
        repository.deleteNote(title)
    }
}