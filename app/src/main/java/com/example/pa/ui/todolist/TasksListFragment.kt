package com.example.pa.ui.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pa.data.DatabaseRepository
import com.example.pa.data.local.PlannerDatabase
import com.example.pa.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {

    private var _binding: FragmentTasksListBinding? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: TasksListViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        restorePreviousState: Bundle?
    ): View {
        val database = PlannerDatabase.getDatabase(requireContext())
        val repository = DatabaseRepository(database.taskDao())
        val factory = TasksListViewModelFactory(repository)

        listViewModel = ViewModelProvider(this, factory).get(TasksListViewModel::class.java)

        _binding = FragmentTasksListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerviewTasksList
        val listAdapter = TasksListAdapter()
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        listViewModel.allTasks.observe(viewLifecycleOwner) {tasks ->
            tasks.let { listAdapter.submitList(it) }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}