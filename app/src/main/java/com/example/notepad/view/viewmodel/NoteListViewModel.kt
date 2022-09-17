package com.example.notepad.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.view.interfaces.NotesRepository
import com.example.notepad.model.NotesRepositoryImpl
import com.example.notepad.model.data.Note
import com.example.notepad.view.interfaces.Responsable

class NoteListViewModel(private val lifeData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel() {

    private val repository: NotesRepository = NotesRepositoryImpl()

    fun getLifeData(): MutableLiveData<AppState> {
        return lifeData
    }

    fun sendRequest() {
//        lifeData.value = AppState.Error(Exception())
        repository.getAllNotes(callBack)
    }

    private val callBack = object : Responsable {
        override fun onResponse(list: List<Note>) {
            lifeData.postValue(AppState.Success(list))
        }
    }

    fun insert(note: Note) {
        repository.insertNote(note)
    }

    fun delete(title: String) {
        repository.deleteNote(title)
    }
}