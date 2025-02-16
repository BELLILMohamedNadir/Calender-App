package com.example.calenderapp.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.calenderapp.R

@Composable
fun DropDownMenu(
    onDelete : () -> Unit,
    onCheck : () -> Unit,
    onModify : () -> Unit
) {
    var expanded by remember { mutableStateOf(false) } 
    var selectedOption by remember { mutableStateOf("Option 1") }

    Box(contentAlignment = Alignment.Center) {
        
        IconButton(onClick = {expanded =! expanded}) {
            Icon(painter = painterResource(id = R.drawable.ic_more), contentDescription = "more")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                selectedOption = "Option 1"
                expanded = false
                onCheck()
            },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "check")
                },
                text = {
                    Text(text = stringResource(id = R.string.check))
                })
            DropdownMenuItem(onClick = {
                selectedOption = "Option 2"
                expanded = false
                onModify()
            },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Create, contentDescription = "modify")
                },
                text = {
                    Text(text = stringResource(id = R.string.modify))
                })
            DropdownMenuItem(onClick = {
                selectedOption = "Option 3"
                expanded = false
                onDelete()
            },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                },
                text = {
                    Text(text = stringResource(id = R.string.delete))
                })
        }
        
    }
}