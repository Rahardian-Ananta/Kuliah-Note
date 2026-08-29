package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.CourseDao
import com.rahardian.kuliahnote.data.db.entity.Course
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {
    fun getCoursesBySemester(semesterId: Long): Flow<List<Course>> = courseDao.getCoursesBySemester(semesterId)
    fun getCourseCount(semesterId: Long): Flow<Int> = courseDao.getCourseCount(semesterId)
    suspend fun getCourseById(id: Long): Course? = courseDao.getCourseById(id)
    suspend fun insert(course: Course): Long = courseDao.insert(course)
    suspend fun update(course: Course) = courseDao.update(course)
    suspend fun delete(course: Course) = courseDao.delete(course)
}
