package com.example.notes.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.ui.theme.NotesTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? =null,
    onNavHomePage: () -> Unit,
    onNavSignUpPage: () -> Unit,
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
            .padding(16.dp),
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
                .padding(16.dp),
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
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavSignUpPage.invoke() }) {
                Text(text = "Signup")

            }
        }
            if (loginUiState?.isLoading == true) {
                CircularProgressIndicator()
            }
        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser==true){
                onNavHomePage.invoke()
            }

        }
    }

}
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? =null,
    onNavHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val loginUiState=loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current



    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Sign Up",

            fontWeight = FontWeight.Black.weight,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary)
        if (isError){
            Text(text = loginUiState?.signUpError?: "unknown error", color = Red,)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userNameSignUp?:"",
            onValueChange = {loginViewModel?.onpasswordChangeSignUp(it)},
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
                .padding(16.dp),
            value = loginUiState?.passwordSignUp?:"",
            onValueChange = {loginViewModel?.onpasswordChangeSignUp(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription =null,
                )
            },

            label = {
                Text(text = "Password")
            },

            isError = isError

        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.confrimPasswordSignUp?:"",
            onValueChange = {loginViewModel?.onpasswordChangeSignUp(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription =null,
                )
            },

            label = {
                Text(text = "Confirm Password")
            },

            isError = isError

        )


        Button(onClick = { loginViewModel?.createUser(context) }) {
            Text(text = "Sign In")

        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "Already has an account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() }) {
                Text(text = "Sign In")

            }
        }
        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser==true){
                onNavHomePage.invoke()
            }

        }
    }

}

fun Text(text: String, style: Any, fontWeight: Int, color: Color) {


}

@Preview(showSystemUi = true)
@Composable
 fun PrevLoginScreen() {
     NotesTheme {

         LoginScreen(onNavHomePage = { /*TODO*/ }) {

         }
     }

}
@Preview(showSystemUi = true)
@Composable
fun PrevSignUpScreen() {
    NotesTheme {

        SignUpScreen(onNavHomePage = { /*TODO*/ }) {

        }
    }

}

