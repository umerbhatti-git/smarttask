package com.example.smarttask.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class TitleViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date> get() = _date

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setDate(date: Date) {
        _date.value = date
    }
}