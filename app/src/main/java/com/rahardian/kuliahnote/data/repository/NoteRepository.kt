package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.NoteDao
import com.rahardian.kuliahnote.data.db.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    fun getNotesByWeek(weekId: Long): Flow<List<Note>> = noteDao.getNotesByWeek(weekId)
    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)
    suspend fun getNoteById(id: Long): Note? = noteDao.getNoteById(id)
    suspend fun insert(note: Note): Long = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
}
