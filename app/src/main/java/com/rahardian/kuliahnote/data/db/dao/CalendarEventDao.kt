package com.rahardian.kuliahnote.data.db.dao

import androidx.room.*
import com.rahardian.kuliahnote.data.db.entity.CalendarEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_events WHERE date = :date")
    fun getEventsByDate(date: String): Flow<List<CalendarEvent>>

    @Query("SELECT * FROM calendar_events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<CalendarEvent>>

    @Insert
    suspend fun insert(event: CalendarEvent): Long

    @Update
    suspend fun update(event: CalendarEvent)

    @Delete
    suspend fun delete(event: CalendarEvent)
}
