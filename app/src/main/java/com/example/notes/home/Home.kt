package com.example.notes.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.notes.models.Notes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick:(id:String) -> Unit,
    navToDetailPage:()-> Unit,
    navToLoginPage:()-> Unit,
) {
    
   val homeUiState = homeViewModel?.homeUiState?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote:Notes? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold(
        scaffoldState =scaffoldState,

         ={

    },
    ) {

    }








}

