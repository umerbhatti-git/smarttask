package com.example.smarttask.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val Description: String?,
    val DueDate: String?,
    val Priority: Int?,
    val TargetDate: String?,
    val Title: String?,
    val id: String?
) : Parcelable