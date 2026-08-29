package com.rahardian.kuliahnote.ui.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahardian.kuliahnote.KuliahNoteApp
import com.rahardian.kuliahnote.data.db.entity.Course
import com.rahardian.kuliahnote.data.db.entity.Semester
import com.rahardian.kuliahnote.data.repository.CourseRepository
import com.rahardian.kuliahnote.data.repository.SemesterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CourseViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as KuliahNoteApp).database
    private val semesterRepo = SemesterRepository(database.semesterDao())
    private val courseRepo = CourseRepository(database.courseDao())

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _courses

    private val _currentSemester = MutableLiveData<Semester?>()
    val currentSemester: LiveData<Semester?> = _currentSemester

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val semester = semesterRepo.currentSemester.first()
            _currentSemester.value = semester
            semester?.let {
                val courseList = courseRepo.getCoursesBySemester(it.id).first()
                _courses.value = courseList
            }
        }
    }

    fun addCourse(name: String, color: String, description: String) {
        viewModelScope.launch {
            val semester = _currentSemester.value ?: return@launch
            courseRepo.insert(Course(semesterId = semester.id, name = name, color = color, description = description))
            loadCourses()
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            courseRepo.delete(course)
            loadCourses()
        }
    }

    fun refresh() = loadCourses()
}
