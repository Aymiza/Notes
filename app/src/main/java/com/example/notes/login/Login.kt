package com.example.notes.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes.ui.theme.NotesTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? =null,
    onNavSignUpPage: () -> Unit,
    onNavHomePage: () -> Unit,
) {
    val loginUiState=loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current



    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Login",

            fontWeight = FontWeight.Black.weight,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary)
        if (isError){
            Text(text = loginUiState?.loginError?: "unknown error", color = Red,)
        }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        value = loginUiState?.userName?:"",
        onValueChange = {loginViewModel?.onUserNameChange(it)},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription =null,
                )
        },

        label = {
            Text(text = "Email")
        },

        isError = isError

    )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            value = loginUiState?.password?:"",
            onValueChange = {loginViewModel?.onPasswordChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription =null,
                )
            },

            label = {
                Text(text = "Password")
            },

            isError = isError

        )
        
        Button(onClick = { loginViewModel?.loginUser(context) }) {
            Text(text = "Sign In")
            
        }
        Spacer(modifier = Modifier.size())

    }


}

fun Text(text: String, style: Any, fontWeight: Int, color: Color) {


}

@Preview(showSystemUi = true)
@Composable
 fun PrevLoginScreen() {
     NotesTheme {

         LoginScreen(onNavSignUpPage = { /*TODO*/ }) {

         }
     }

}

