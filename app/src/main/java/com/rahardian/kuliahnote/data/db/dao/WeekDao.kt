package com.rahardian.kuliahnote.data.db.dao

import androidx.room.*
import com.rahardian.kuliahnote.data.db.entity.Week
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekDao {
    @Query("SELECT * FROM weeks WHERE courseId = :courseId ORDER BY weekNumber ASC")
    fun getWeeksByCourse(courseId: Long): Flow<List<Week>>

    @Query("SELECT * FROM weeks WHERE id = :id")
    suspend fun getWeekById(id: Long): Week?

    @Query("SELECT * FROM weeks WHERE courseId = :courseId AND weekNumber = :weekNumber")
    suspend fun getWeekByNumber(courseId: Long, weekNumber: Int): Week?

    @Insert
    suspend fun insert(week: Week): Long

    @Update
    suspend fun update(week: Week)

    @Delete
    suspend fun delete(week: Week)
}
