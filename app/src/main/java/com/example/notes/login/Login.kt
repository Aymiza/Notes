package com.example.notes.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable

fun LoginScreen (
    loginViewModel: LoginViewModel? =null,
    onNavToHomePage() -> Unit,
    onNavToSignUpPage:() -> Unit,
){

    val loginUiState = loginViewModel?.loginUiState
    val isError =   loginUiState?.loginError != null
    val context = LocalContext.current

    Column(
                modifier = Modeifier.fillMaxSize()
                horozontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(text = "Login",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black.weight,
            color = MaterialTheme.colors.primary
        )

        if (isError){
            Text(text = loginUiState?.loginError): "unknown error",
            color = Color.Red,
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen()(
    NotesTheme {
        LoginScreen(onNavToHomePage = ){

        }
    }
)