package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.SemesterDao
import com.rahardian.kuliahnote.data.db.entity.Semester
import kotlinx.coroutines.flow.Flow

class SemesterRepository(private val semesterDao: SemesterDao) {
    val allSemesters: Flow<List<Semester>> = semesterDao.getAllSemesters()
    val currentSemester: Flow<Semester?> = semesterDao.getCurrentSemester()

    suspend fun getSemesterById(id: Long): Semester? = semesterDao.getSemesterById(id)
    suspend fun insert(semester: Semester): Long = semesterDao.insert(semester)
    suspend fun update(semester: Semester) = semesterDao.update(semester)
    suspend fun delete(semester: Semester) = semesterDao.delete(semester)
}
