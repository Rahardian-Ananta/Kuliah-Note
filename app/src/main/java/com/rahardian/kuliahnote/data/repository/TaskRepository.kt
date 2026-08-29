package com.rahardian.kuliahnote.data.repository

import com.rahardian.kuliahnote.data.db.dao.TaskDao
import com.rahardian.kuliahnote.data.db.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    fun getTasksByWeek(weekId: Long): Flow<List<Task>> = taskDao.getTasksByWeek(weekId)
    fun getPendingTasks(): Flow<List<Task>> = taskDao.getPendingTasks()
    suspend fun getTaskById(id: Long): Task? = taskDao.getTaskById(id)
    suspend fun insert(task: Task): Long = taskDao.insert(task)
    suspend fun update(task: Task) = taskDao.update(task)
    suspend fun delete(task: Task) = taskDao.delete(task)
}
