package com.example.notes.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? =null,
    onNavToHomePage: () -> Unit,
    onNavSignUpPage: () -> Unit,
) {
    val loginUiState=loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Login",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black.weight,
            color = MaterialTheme.colorScheme.primary)
        if (isError){
            Text(text = loginUiState?.loginError?: "unknown error", color = Red,)
        }
    }


}

