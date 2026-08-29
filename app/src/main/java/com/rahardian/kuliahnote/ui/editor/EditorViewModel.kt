package com.rahardian.kuliahnote.ui.editor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahardian.kuliahnote.KuliahNoteApp
import com.rahardian.kuliahnote.data.db.entity.Note
import com.rahardian.kuliahnote.data.repository.NoteRepository
import kotlinx.coroutines.launch

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as KuliahNoteApp).database
    private val noteRepo = NoteRepository(database.noteDao())

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun loadNote(noteId: Long) {
        if (noteId <= 0) return
        viewModelScope.launch {
            _note.value = noteRepo.getNoteById(noteId)
        }
    }

    fun saveNote(title: String, content: String, weekId: Long) {
        viewModelScope.launch {
            val existing = _note.value
            if (existing != null) {
                noteRepo.update(existing.copy(title = title, content = content, updatedAt = System.currentTimeMillis()))
            } else {
                noteRepo.insert(Note(weekId = weekId, title = title, content = content))
            }
            _saved.value = true
        }
    }
}
