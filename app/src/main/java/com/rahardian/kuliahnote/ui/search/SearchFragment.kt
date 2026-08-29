package com.rahardian.kuliahnote.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.ui.adapter.NoteAdapter

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchInput = view.findViewById<EditText>(R.id.search_input)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_results)
        val emptyText = view.findViewById<TextView>(R.id.empty_text)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NoteAdapter { note ->
            val bundle = Bundle().apply {
                putLong("noteId", note.id)
                putLong("weekId", note.weekId)
            }
            findNavController().navigate(R.id.editorFragment, bundle)
        }
        recyclerView.adapter = adapter

        searchInput.addTextChangedListener { text ->
            viewModel.search(text.toString())
        }

        viewModel.results.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
            emptyText.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            recyclerView.visibility = if (notes.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}
