package com.rahardian.kuliahnote.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.ui.adapter.CourseAdapter

class CourseListFragment : Fragment() {
    private val viewModel: CourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.course_recycler_view)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_course)

        val adapter = CourseAdapter { course ->
            val bundle = Bundle().apply { putLong("courseId", course.id) }
            findNavController().navigate(R.id.courseDetailFragment, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.updateCourses(courses)
        }

        fab.setOnClickListener {
            showAddCourseDialog()
        }
    }

    private fun showAddCourseDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_course, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.edit_course_name)
        val descInput = dialogView.findViewById<EditText>(R.id.edit_course_description)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Mata Kuliah")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val name = nameInput.text.toString()
                val desc = descInput.text.toString()
                if (name.isNotBlank()) {
                    viewModel.addCourse(name, "#4D96FF", desc)
                } else {
                    Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
