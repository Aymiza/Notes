package com.example.notes.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailViewModel: detailViewModel?,
    noteId: String,
    onNavigate:() -> Unit
) {
    val detailUiState = detailViewModel?.detailUiState?: DetailUiState()

    val isFormsNotBlank = detailUiState.note.isNotBlank() &&
            detailUiState.title.isNotBlank()

    val selectedColor by animateColorAsState(targetValue = Utils.colors[detailUiState.colorIndex],
        label = ""
    )
    val isNoteIdNotBlank =  noteId.isNotBlank()
    val icon = if(isFormsNotBlank) Icons.Default.Refresh
    else Icons.Default.Check

    LaunchedEffect(key1 = Unit) {
        if (isNoteIdNotBlank){
            detailViewModel?.getNote(noteId)
        }else {
            detailViewModel?.resetState()
        }

    }
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold (floatingActionButton = {
        FloatingActionButton(onClick = {if (isNoteIdNotBlank){
        detailViewModel?.updateNote(noteId)/*TODO*/
        } })
        {
            Icon(imageVector = icon, contentDescription = null)
        }
    })

}