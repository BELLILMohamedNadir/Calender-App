package com.example.calenderapp.ui.theme.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calenderapp.R
import com.example.calenderapp.ui.theme.components.CustomCardView
import com.example.calenderapp.ui.theme.components.CustomDatePicker
import com.example.calenderapp.ui.theme.components.CustomTopAppBar
import com.example.calenderapp.ui.theme.components.FloatingButton
import com.example.calenderapp.ui.theme.models.Note
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, notes: MutableMap<String, MutableList<Note>>) {
    val context = LocalContext.current
    val items = remember {
        mutableStateOf(notes)
    }

    var isCalenderScreenShow by remember {
        mutableStateOf(true)
    }

    var isAddNoteScreenShow by remember {
        mutableStateOf(false)
    }

    var isModifyNoteScreenShow by remember {
        mutableStateOf(false)
    }

    val aNote = remember {
        mutableStateOf(Note(date = "", title =  "", description = ""))
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val selectedDate = remember {
        mutableStateOf("")
    }

    Box(modifier = modifier.fillMaxSize()) {
        //Calender Screen (Home Screen)
        if (isCalenderScreenShow){
            CalenderScreen(
                onAddNote = {
                    isCalenderScreenShow = false
                    isAddNoteScreenShow = true
                },
                onModifyNoteCLick = {note ->
                    aNote.value = note
                    isCalenderScreenShow = false
                    isModifyNoteScreenShow = true
                },
                onDeleteNoteClick = { note ->
                    items.value = items.value.toMutableMap().apply {
                        // Remove the note from the list associated with the date
                        this[note.date] = (this[note.date] ?: emptyList())
                            .filter { it.id != note.id }
                            .toMutableList()

                        // If the resulting list is empty, remove the key entirely
                        if (this[note.date].isNullOrEmpty()) {
                            this.remove(note.date)
                        }
                    }
                },
                onSelectedDate = { miles ->
                    selectedDate.value = miles.format(formatter).toString()
                }
                ,
                items = if (items.value[selectedDate.value] != null) items.value[selectedDate.value]!!.toList() else emptyList()
            )
        }

        //Modify Screen
        if (isModifyNoteScreenShow){
            NoteScreen(
                note = aNote.value,
                onDismiss = {
                isModifyNoteScreenShow = false
                isCalenderScreenShow = true
            }) { note ->

                items.value = items.value.toMutableMap().apply {
                    // Get the current list of notes for the date, or an empty list if it doesn't exist
                    val currentList = this[note.date] ?: emptyList()

                    // Check if the note already exists (for updating)
                    val updatedList = if (currentList.any { it.id == note.id }) {
                        // If the note exists, replace it with the updated note
                        currentList.map { if (it.id == note.id) note else it }.toMutableList()
                    } else {
                        // If the note doesn't exist, add it to the list
                        (currentList + note).toMutableList()
                    }

                    this[note.date] = updatedList

                    isModifyNoteScreenShow = false
                    isCalenderScreenShow = true
                    //the set the calender in the selected date
                    selectedDate.value = note.date
                }
            }
        }

        // Add note Screen
        if (isAddNoteScreenShow){
                NoteScreen(
                    onDismiss = {
                        isAddNoteScreenShow = false
                        isCalenderScreenShow = true
                    }
                ) {note ->
                    items.value = items.value.toMutableMap().apply {
                        val currentList = this[note.date] ?: emptyList()
                        this[note.date] = (currentList + note).toMutableList()
                    }
                    isAddNoteScreenShow = false
                    isCalenderScreenShow = true
                    //the set the calender in the selected date
                    selectedDate.value = note.date
                }
            }
        }
    }
