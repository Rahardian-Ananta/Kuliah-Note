package com.rahardian.kuliahnote.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahardian.kuliahnote.KuliahNoteApp
import com.rahardian.kuliahnote.data.db.entity.Course
import com.rahardian.kuliahnote.data.db.entity.Semester
import com.rahardian.kuliahnote.data.db.entity.Task
import com.rahardian.kuliahnote.data.repository.CourseRepository
import com.rahardian.kuliahnote.data.repository.SemesterRepository
import com.rahardian.kuliahnote.data.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as KuliahNoteApp).database
    private val semesterRepo = SemesterRepository(database.semesterDao())
    private val courseRepo = CourseRepository(database.courseDao())
    private val taskRepo = TaskRepository(database.taskDao())

    private val _currentSemester = MutableLiveData<Semester?>()
    val currentSemester: LiveData<Semester?> = _currentSemester

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _courses

    private val _pendingTasks = MutableLiveData<List<Task>>()
    val pendingTasks: LiveData<List<Task>> = _pendingTasks

    private val _greeting = MutableLiveData<String>()
    val greeting: LiveData<String> = _greeting

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val semester = semesterRepo.currentSemester.first()
            _currentSemester.value = semester

            semester?.let {
                val courseList = courseRepo.getCoursesBySemester(it.id).first()
                _courses.value = courseList
            }

            val tasks = taskRepo.getPendingTasks().first()
            _pendingTasks.value = tasks

            _greeting.value = getGreeting()
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            taskRepo.update(task.copy(isCompleted = !task.isCompleted))
            loadData()
        }
    }

    private fun getGreeting(): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> "Selamat Pagi"
            hour < 17 -> "Selamat Siang"
            hour < 21 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
    }

    fun refresh() = loadData()
}
