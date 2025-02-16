package com.example.calenderapp.ui.theme.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.UUID

data class Note(
    var date: String,
    var title: String,
    var description: String,
    val id: String = UUID.randomUUID().toString(),
    val isEnabled: MutableState<Boolean> = mutableStateOf(true)
)