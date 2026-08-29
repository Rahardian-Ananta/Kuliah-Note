package com.rahardian.kuliahnote.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.data.db.entity.Course

class CourseAdapter(
    private var courses: List<Course> = emptyList(),
    private val onClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private val courseColors = mapOf(
        "#4D96FF" to 75,
        "#6BCB77" to 60,
        "#FF9F45" to 40,
        "#B08BFF" to 80,
        "#FF6B9D" to 55,
        "#FFD93D" to 30,
        "#FF4444" to 20
    )

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.course_name)
        val description: TextView = view.findViewById(R.id.course_description)
        val progressBar: ProgressBar = view.findViewById(R.id.course_progress)
        val progressText: TextView = view.findViewById(R.id.course_progress_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.name.text = course.name
        holder.description.text = course.description
        val progress = courseColors[course.color] ?: 50
        holder.progressBar.progress = progress
        holder.progressText.text = "$progress%"
        holder.itemView.setOnClickListener { onClick(course) }
    }

    override fun getItemCount() = courses.size

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
