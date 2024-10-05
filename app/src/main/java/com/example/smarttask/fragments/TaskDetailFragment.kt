package com.example.smarttask.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smarttask.R
import com.example.smarttask.adapters.MainRVAdapter.Companion.getRemainingDays
import com.example.smarttask.databinding.FragmentTaskDetailBinding
import com.example.smarttask.models.Task

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private var task: Task? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = requireArguments().getParcelable("task") as? Task
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task?.let {
            binding.tvTitle.text = it.Title
            binding.tvDate.text = it.DueDate
            binding.tvDays.text = getRemainingDays(it.TargetDate, it.DueDate)
            binding.tvDetail.text = it.Description
        }

        binding.btnResolve.setOnClickListener {
            binding.llBtns.visibility = View.GONE
            binding.ivStatus.visibility = View.VISIBLE
            binding.ivStatus.setImageResource(R.drawable.sign_resolved)
            binding.tvStatus.text = "Resolved"
            binding.tvStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        binding.btnCantResolve.setOnClickListener {
            binding.llBtns.visibility = View.GONE
            binding.ivStatus.visibility = View.VISIBLE
            binding.ivStatus.setImageResource(R.drawable.unresolved_sign)
            binding.tvStatus.text = "Unresolved"
            binding.tvStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }
}