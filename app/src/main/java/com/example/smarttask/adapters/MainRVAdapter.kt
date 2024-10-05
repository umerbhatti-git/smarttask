package com.example.smarttask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttask.databinding.MainItemBinding
import com.example.smarttask.models.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainRVAdapter(
    private val itemList: List<Task>,
    private val taskClickListener: OnTaskClickListener
) : RecyclerView.Adapter<MainRVAdapter.ItemViewHolder>() {

    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
    }

    companion object{
        fun getRemainingDays(targetDateStr: String?, dueDateStr: String?): String {
            if (targetDateStr == null || dueDateStr == null)
                return "0"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val targetDate = LocalDate.parse(targetDateStr, formatter)
            val dueDate = LocalDate.parse(dueDateStr, formatter)
            val remainingDays = ChronoUnit.DAYS.between(targetDate, dueDate)
            return remainingDays.toString()
        }
    }

    class ItemViewHolder(
        private val binding: MainItemBinding,
        private val taskClickListener: OnTaskClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTitle.text = item.Title
            binding.tvDate.text = item.DueDate
            binding.tvDays.text = getRemainingDays(item.TargetDate, item.DueDate)
            binding.root.setOnClickListener {
                taskClickListener.onTaskClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, taskClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
