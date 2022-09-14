package com.example.notepad.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.model.NotesRepository
import com.example.notepad.model.NotesRepositoryImpl

class NotesViewModel(private val lifeData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel() {

    private val repository: NotesRepository = NotesRepositoryImpl()

    fun getLifeData(): MutableLiveData<AppState> {
        return lifeData
    }

    fun sentRequest() {
//        lifeData.value = AppState.Loading
//        lifeData.value = AppState.Error(Exception())
        lifeData.value = AppState.Success(repository.getNotes())
    }
}