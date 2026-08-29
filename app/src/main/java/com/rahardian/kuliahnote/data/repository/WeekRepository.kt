package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.WeekDao
import com.rahardian.kuliahnote.data.db.entity.Week
import kotlinx.coroutines.flow.Flow

class WeekRepository(private val weekDao: WeekDao) {
    fun getWeeksByCourse(courseId: Long): Flow<List<Week>> = weekDao.getWeeksByCourse(courseId)
    suspend fun getWeekById(id: Long): Week? = weekDao.getWeekById(id)
    suspend fun getWeekByNumber(courseId: Long, weekNumber: Int): Week? = weekDao.getWeekByNumber(courseId, weekNumber)
    suspend fun insert(week: Week): Long = weekDao.insert(week)
    suspend fun update(week: Week) = weekDao.update(week)
    suspend fun delete(week: Week) = weekDao.delete(week)
}
