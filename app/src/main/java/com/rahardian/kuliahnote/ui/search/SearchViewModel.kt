package com.rahardian.kuliahnote.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahardian.kuliahnote.KuliahNoteApp
import com.rahardian.kuliahnote.data.db.entity.Note
import com.rahardian.kuliahnote.data.repository.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as KuliahNoteApp).database
    private val noteRepo = NoteRepository(database.noteDao())

    private val _results = MutableLiveData<List<Note>>()
    val results: LiveData<List<Note>> = _results

    fun search(query: String) {
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }
        viewModelScope.launch {
            val searchResults = noteRepo.searchNotes(query).first()
            _results.postValue(searchResults)
        }
    }
}
