package com.example.calenderapp.ui.theme.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calenderapp.R
import com.example.calenderapp.ui.theme.models.Note

@Composable
fun CustomCardView(
    modifier: Modifier = Modifier,
    note: Note,
    color: Color = colorResource(id = R.color.purple),
    onDropDownModifyClick : (Note) -> Unit,
    onDropDownDeleteClick : (Note) -> Unit,
    onCardViewClick: (Note) ->Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val maxLength = 35
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onCardViewClick(note) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .animateContentSize()
            ) {

                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /*TODO*/ },
                            colors = IconButtonDefaults
                                .iconButtonColors(
                                    contentColor = if (note.isEnabled.value) color else Color.Gray
                                )) {
                            Icon(painter = painterResource(id = R.drawable.ic_calender), contentDescription = "calender")
                        }
                        Text(text = note.date, fontSize = 12.sp)
                    }
                    DropDownMenu(
                        onCheck = {
                            note.isEnabled.value=! note.isEnabled.value
                        },
                        onModify = {
                            onDropDownModifyClick(note)
                        },
                        onDelete = {
                            onDropDownDeleteClick(note)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Body Content
                Column(modifier = Modifier.padding(horizontal = 12.dp)) {

                    Text(text = note.title, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        color = if (note.isEnabled.value) Color.Black else Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    val text = buildAnnotatedString {
                        if (expanded) append(note.description) else append(note.description.take(maxLength) + "...")
                        append(" ")
                        if (note.description.length > maxLength){
                            withStyle(style = SpanStyle(color = if (note.isEnabled.value) color else Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)){
                                if (!expanded)
                                    append(stringResource(id = R.string.view_more))
                                else
                                    append(stringResource(id = R.string.view_less))
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    expanded = !expanded
                                }
                            },
                        text = text,
                        fontSize = 14.sp,
                        color = if (note.isEnabled.value) Color.Black else Color.Gray,
                        maxLines = if (expanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (!note.isEnabled.value)
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height((1.5).dp)
                    .background(color = Color.Gray)
                    .align(Alignment.Center))
        }
    }
}
