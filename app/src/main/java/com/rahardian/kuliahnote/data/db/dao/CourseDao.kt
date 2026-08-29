package com.rahardian.kuliahnote.data.db.dao

import androidx.room.*
import com.rahardian.kuliahnote.data.db.entity.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses WHERE semesterId = :semesterId ORDER BY name ASC")
    fun getCoursesBySemester(semesterId: Long): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Long): Course?

    @Query("SELECT COUNT(*) FROM courses WHERE semesterId = :semesterId")
    fun getCourseCount(semesterId: Long): Flow<Int>

    @Insert
    suspend fun insert(course: Course): Long

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)
}
