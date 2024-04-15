package com.example.notes.detail

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isNoteIdNotBlank){
                        detailViewModel?.updateNote(noteId)
                    }else{
                        detailViewModel?.addNotes()
                    }
                }
            )
            {
                Icon(imageVector = icon, contentDescription = null)
            }
        },

    ) {padding ->
     Column(
         modifier = Modifier.fillMaxSize()
             .background(color=selectedColor)
             .padding(padding)
     ){
         if (detailUiState.noteAddedStatus){
             scope.launch {
                 scaffoldState.snackbarHostState
                     .showSnackbar("Addedd Note")
                 detailViewModel?.resetState()
                 onNavigate.invoke()
             }
         }

         if(detailUiState.updateNoteStatus){
             scope.launch {
                 scaffoldState.snackbarHostState
                     .showSnackbar("Note Updated Successfully")
                 detailViewModel?.resetNoteAddedStatus()
                 onNavigate.invoke()
             }
         }
     }

    }

}

fun Column(modifier: Modifier) {

}
@Preview
@Composable
fun SimpleComposablePreview() {
    SimpleComposable()
}

@Composable
fun SimpleComposable() {
    TODO("Not yet implemented")
}
