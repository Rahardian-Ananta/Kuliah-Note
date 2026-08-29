package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.CalendarEventDao
import com.rahardian.kuliahnote.data.db.entity.CalendarEvent
import kotlinx.coroutines.flow.Flow

class CalendarEventRepository(private val eventDao: CalendarEventDao) {
    fun getEventsByDate(date: String): Flow<List<CalendarEvent>> = eventDao.getEventsByDate(date)
    fun getAllEvents(): Flow<List<CalendarEvent>> = eventDao.getAllEvents()
    suspend fun insert(event: CalendarEvent): Long = eventDao.insert(event)
    suspend fun update(event: CalendarEvent) = eventDao.update(event)
    suspend fun delete(event: CalendarEvent) = eventDao.delete(event)
}
