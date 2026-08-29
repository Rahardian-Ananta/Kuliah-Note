package com.rahardian.kuliahnote.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.ui.adapter.NoteAdapter

class CourseDetailFragment : Fragment() {
    private val viewModel: CourseDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val courseId = arguments?.getLong("courseId", 0L) ?: 0L
        viewModel.loadCourse(courseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleText = view.findViewById<TextView>(R.id.detail_title)
        val noteRecyclerView = view.findViewById<RecyclerView>(R.id.note_recycler_view)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_note)

        noteRecyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = NoteAdapter { note ->
            val bundle = Bundle().apply {
                putLong("noteId", note.id)
                putLong("weekId", note.weekId)
            }
            findNavController().navigate(R.id.editorFragment, bundle)
        }
        noteRecyclerView.adapter = adapter

        viewModel.course.observe(viewLifecycleOwner) { course ->
            course?.let { titleText.text = it.name }
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
        }

        fab.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val input = EditText(requireContext()).apply {
            hint = "Judul catatan"
            setPadding(48, 32, 48, 32)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Catatan")
            .setView(input)
            .setPositiveButton("Simpan") { _, _ ->
                val title = input.text.toString()
                if (title.isNotBlank()) {
                    val week = viewModel.weeks.value?.firstOrNull()
                    if (week != null) {
                        viewModel.addNote(week.id, title)
                    } else {
                        Toast.makeText(context, "Buat minggu terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
