package com.example.smarttask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttask.R
import com.example.smarttask.adapters.MainRVAdapter
import com.example.smarttask.databinding.FragmentHomeBinding
import com.example.smarttask.models.Task
import com.example.smarttask.retrofit.RetrofitClient
import com.example.smarttask.viewModels.TitleViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(), MainRVAdapter.OnTaskClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var controller: NavController
    private val titleViewModel: TitleViewModel by activityViewModels()
    private var taskList: List<Task> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        titleViewModel.date.observe(viewLifecycleOwner) { date1 ->
            if (taskList.isNotEmpty()) {
                filterTasksByDate(date1)
            } else {
                fetchData(date1)
            }
        }
    }

    private fun fetchData(date1: Date) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getData()
                taskList = response.tasks
                filterTasksByDate(date1)
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun filterTasksByDate(date1: Date) {
        val filteredTasks = taskList.filter { task ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calDate = dateFormat.format(date1)
//            val taskDate: String? = task.DueDate
            val taskDate: String? = task.TargetDate
            taskDate == calDate
        }

        binding.llNoTasks.visibility = if (filteredTasks.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = MainRVAdapter(filteredTasks, this@HomeFragment)
    }

    override fun onTaskClick(task: Task) {
        val bundle = Bundle()
        bundle.putParcelable("task", task)
        controller.navigate(R.id.action_firstFragment_to_taskDetail, bundle)
    }
}