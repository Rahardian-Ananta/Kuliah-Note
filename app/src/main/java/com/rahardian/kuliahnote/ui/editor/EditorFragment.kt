package com.rahardian.kuliahnote.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rahardian.kuliahnote.R

class EditorFragment : Fragment() {
    private val viewModel: EditorViewModel by viewModels()

    private var noteId: Long = -1L
    private var weekId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = arguments?.getLong("noteId", -1L) ?: -1L
        weekId = arguments?.getLong("weekId", 0L) ?: 0L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleInput = view.findViewById<EditText>(R.id.editor_title)
        val contentInput = view.findViewById<EditText>(R.id.editor_content)
        val saveFab = view.findViewById<FloatingActionButton>(R.id.fab_save)

        if (noteId > 0) {
            viewModel.loadNote(noteId)
        }

        viewModel.note.observe(viewLifecycleOwner) { note ->
            note?.let {
                titleInput.setText(it.title)
                contentInput.setText(it.content)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Toast.makeText(context, "Tersimpan!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        saveFab.setOnClickListener {
            val title = titleInput.text.toString()
            val content = contentInput.text.toString()
            if (title.isNotBlank()) {
                viewModel.saveNote(title, content, weekId)
            } else {
                Toast.makeText(context, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
