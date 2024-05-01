package com.example.notes.home

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notes.detail.Utils
import com.example.notes.models.Notes
import com.example.notes.repository.Resources
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes.ui.theme.NotesTheme
import com.google.firebase.Timestamp

import java.text.SimpleDateFormat
import java.util.Locale

//import java.text.SimpleDateFormat
//import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit,
) {

    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotes()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navToDetailPage.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )

            }
        },
        topBar = {
            TopAppBar(
                navigationIcon = { },
                actions = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navToLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = "Home")
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding()) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }


                is Resources.Success -> {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp)
                        ) {
                       items(homeUiState.notesList.data?: emptyList()){note ->
                           NoteItem(
                               notes =note,
                               onLongClick = {
                                             openDialog = true
                                   selectedNote = note
                               },
                           ){
                               onNoteClick.invoke(note.documentId)
                           }

                        }
                    }
                }

                else -> {
                    Text(
                        text = homeUiState
                            .notesList.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Red
                    )
                }

            }
        }

    }
    LaunchedEffect(key1 = homeViewModel?.hasUser) {
        if (homeViewModel?.hasUser ==false){
            navToLoginPage.invoke()
        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
//        val LocalContentAlpha = compositionLocalOf { 1F }
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = {
                    onLongClick.invoke()
                },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Utils.colors[notes.colorIndex])

        //backgroundColor =Utils.colors[notes.colorIndex]
    ) {

        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))

            CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
                Text(
                    text = notes.description,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 4
                )
            }

            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
                Text(
                    text = formatDate(notes.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End),
                    maxLines = 4
                )
            }

        }
    }

}


private fun formatDate(timestamp: Timestamp):String{
    val sdf = SimpleDateFormat("MM-dd-yy hh:mm", Locale.getDefault())
  return sdf.format(timestamp.toDate())
}

@Preview
@Composable
 fun PreviewHomeScreen() {
    NotesTheme {
        Home(homeViewModel = null, onNoteClick = {},
            navToDetailPage = { /*TODO*/ }
        ) {

        }
    }
}
