package com.rahardian.kuliahnote.data.db.dao

import androidx.room.*
import com.rahardian.kuliahnote.data.db.entity.Semester
import kotlinx.coroutines.flow.Flow

@Dao
interface SemesterDao {
    @Query("SELECT * FROM semesters ORDER BY startDate DESC")
    fun getAllSemesters(): Flow<List<Semester>>

    @Query("SELECT * FROM semesters WHERE id = :id")
    suspend fun getSemesterById(id: Long): Semester?

    @Insert
    suspend fun insert(semester: Semester): Long

    @Update
    suspend fun update(semester: Semester)

    @Delete
    suspend fun delete(semester: Semester)

    @Query("SELECT * FROM semesters ORDER BY startDate DESC LIMIT 1")
    fun getCurrentSemester(): Flow<Semester?>
}
