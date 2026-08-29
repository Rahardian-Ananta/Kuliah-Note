package com.rahardian.kuliahnote.ui.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahardian.kuliahnote.KuliahNoteApp
import com.rahardian.kuliahnote.data.db.entity.Course
import com.rahardian.kuliahnote.data.db.entity.Note
import com.rahardian.kuliahnote.data.db.entity.Week
import com.rahardian.kuliahnote.data.repository.CourseRepository
import com.rahardian.kuliahnote.data.repository.NoteRepository
import com.rahardian.kuliahnote.data.repository.WeekRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CourseDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as KuliahNoteApp).database
    private val courseRepo = CourseRepository(database.courseDao())
    private val weekRepo = WeekRepository(database.weekDao())
    private val noteRepo = NoteRepository(database.noteDao())

    private var courseId: Long = 0L

    private val _course = MutableLiveData<Course?>()
    val course: LiveData<Course?> = _course

    private val _weeks = MutableLiveData<List<Week>>()
    val weeks: LiveData<List<Week>> = _weeks

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun loadCourse(id: Long) {
        courseId = id
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _course.value = courseRepo.getCourseById(courseId)
            val weekList = weekRepo.getWeeksByCourse(courseId).first()
            _weeks.value = weekList

            val allNotes = mutableListOf<Note>()
            for (week in weekList) {
                val weekNotes = noteRepo.getNotesByWeek(week.id).first()
                allNotes.addAll(weekNotes)
            }
            _notes.value = allNotes
        }
    }

    fun addWeek(weekNumber: Int, title: String) {
        viewModelScope.launch {
            weekRepo.insert(Week(courseId = courseId, weekNumber = weekNumber, title = title))
            loadData()
        }
    }

    fun addNote(weekId: Long, title: String) {
        viewModelScope.launch {
            noteRepo.insert(Note(weekId = weekId, title = title))
            loadData()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepo.delete(note)
            loadData()
        }
    }

    fun refresh() = loadData()
}
