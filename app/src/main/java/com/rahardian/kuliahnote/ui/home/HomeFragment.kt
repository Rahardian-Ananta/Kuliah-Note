package com.rahardian.kuliahnote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.ui.adapter.TaskAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val greetingText = view.findViewById<TextView>(R.id.greeting_text)
        val dateText = view.findViewById<TextView>(R.id.date_text)
        val coursesCountText = view.findViewById<TextView>(R.id.courses_count_text)
        val tasksCountText = view.findViewById<TextView>(R.id.tasks_count_text)
        val taskRecyclerView = view.findViewById<RecyclerView>(R.id.task_recycler_view)

        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
        dateText.text = dateFormat.format(Date())

        taskRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.greeting.observe(viewLifecycleOwner) { greeting ->
            greetingText.text = greeting
        }

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            coursesCountText.text = "${courses.size} mata kuliah hari ini"
        }

        viewModel.pendingTasks.observe(viewLifecycleOwner) { tasks ->
            tasksCountText.text = "${tasks.size} tugas aktif"
            taskRecyclerView.adapter = TaskAdapter(tasks) { task ->
                viewModel.toggleTask(task)
            }
        }
    }
}
