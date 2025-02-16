package com.example.calenderapp.ui.theme.screens

import android.os.Build
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import java.time.LocalDate
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalenderScreen(
    modifier: Modifier = Modifier,
    onAddNote: () -> Unit,
    onDeleteNoteClick: (Note) -> Unit,
    onModifyNoteCLick: (Note) ->Unit,
    onSelectedDate: (LocalDate) ->Unit,
    items: List<Note>){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTopAppBar(
            onNavigationClick = {},
            onActionClick = {}
        )
        Column(modifier = modifier
            .fillMaxSize()) {
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.translationY = 0f
                        this.alpha = 1f
                    },
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    CustomDatePicker(){ milis ->
                        onSelectedDate(milis)
                    }
                    Text(
                        text = "Notes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
                if (items.isEmpty()) {
                    item{
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(modifier = modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center){
                            Image(painter = painterResource(id = R.drawable.ic_add_note), contentDescription = "note")
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }else{
                    items(
                        items = items,
                        key = {it.id}
                    ) { item ->
                        CustomCardView(
                            note = item,
                            color =
                            Color(
                                red = Random.nextFloat(),
                                green = Random.nextFloat(),
                                blue = Random.nextFloat(),
                                alpha = 1f
                            ),
                            onDropDownModifyClick = { note ->
                                onModifyNoteCLick(note)
                            },
                            onDropDownDeleteClick = { note ->
                                onDeleteNoteClick(note)
                            }
                        ){note ->
                            onModifyNoteCLick(note)
                        }
                    }
                }
            }
        }
    }
    FloatingButton {
        onAddNote()
    }
}