package com.example.calenderapp.ui.theme.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calenderapp.ui.theme.components.CustomDatePicker
import com.example.calenderapp.ui.theme.components.CustomDatePickerDialog
import com.example.calenderapp.ui.theme.components.CustomTextField
import com.example.calenderapp.ui.theme.components.CustomTopAppBar
import com.example.calenderapp.ui.theme.models.Note
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    note: Note = Note("","",""),
    onDismiss: (Boolean) -> Unit,
    onAddNote : (Note) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val context = LocalContext.current
        var showDatePicker by remember { mutableStateOf(false) }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        var defaultDate = LocalDate.now()
        if (note.date.isNotEmpty() && note.date.isNotBlank())
             defaultDate = LocalDate.parse(note.date, formatter)

        var selectedDate by remember { mutableStateOf(defaultDate) }
        var title by remember { mutableStateOf(note.title) }
        var description by remember { mutableStateOf(note.description) }

        CustomTopAppBar(
            navigationIcon = Icons.Filled.ArrowBack,
            actionIcon = Icons.Filled.Check,
            onNavigationClick = {
                onDismiss(true)
            },
            onActionClick = {

                if (
                    title.isNotEmpty() && title.isNotBlank()
                    &&
                    description.isNotEmpty() && description.isNotBlank()
                    &&
                    selectedDate.toString().isNotEmpty() && selectedDate.toString().isNotBlank()
                    ){
                    onAddNote(Note(id = note.id, title = title, description = description, date = selectedDate.toString()))
                }
            }
        )

       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text(
               text = if (note.date.isNotEmpty() && note.date.isNotBlank()) note.date else " ${selectedDate.format(formatter)}",
               modifier = Modifier.padding(16.dp)
           )

           IconButton(
               onClick = { showDatePicker = !showDatePicker },
               modifier = Modifier
                   .offset(x = (-5).dp)
           ) {
               Icon(imageVector = Icons.Filled.DateRange, contentDescription = "calendar")
           }
       }

        CustomTextField(
            label = "Title",
            description = title,
            maxLines = 1,
            onValueChanged = { title = it },
            modifier = Modifier.padding(2.dp)
        )

        CustomTextField(
            label = "Description",
            description = description,
            onValueChanged = { description = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .weight(1f)
        )

        if (showDatePicker) {
            CustomDatePickerDialog(
                onDateSelected = { dateMillis ->
                    dateMillis?.let {
                        selectedDate = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}