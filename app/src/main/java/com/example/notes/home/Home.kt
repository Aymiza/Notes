package com.example.notes.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.notes.R
import com.example.notes.models.Notes
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(onClick = { homeViewModel?.signOut()
                    navToLoginPage.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    // You can add actions here
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Floating Action Button clicked")
                    }
                },
                text = { Text("FAB") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add"
                    )
                }
            )
        },
        snackbarHost = { hostState ->
            SnackbarHost(
                hostState = hostState,
                modifier = Modifier.fillMaxSize()
            )
        },
        content = {
            // Your scaffold content goes here
            // For example:
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Snackbar message")
                }
            }) {
                Text("Show Snackbar")
            }
        }
    )


}

@Composable
fun Button(onClick: () -> Job, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

fun SnackbarHost(hostState: Any?, modifier: Unit) {

}

class Modifier {
    companion object {
        fun fillMaxSize() {

        }
    }

}

fun Scaffold(topBar: @Composable () -> Unit, floatingActionButton: @Composable () -> Unit, snackbarHost: @Composable (Any?) -> Unit, content: @Composable (PaddingValues) -> Unit) {

}

@Composable
fun Icon(painter: Any, contentDescription: String) {
    TODO("Not yet implemented")
}

@Composable
fun ExtendedFloatingActionButton(onClick: () -> Job, text: @Composable () -> Unit, icon: @Composable () -> Unit) {

}

@Composable
fun TopAppBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit, actions: () -> Unit) {

}





