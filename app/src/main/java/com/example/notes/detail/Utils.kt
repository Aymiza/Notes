package com.example.notes.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes.login.LoginScreen
import com.example.notes.ui.theme.NotesTheme

object Utils {
    val colors =listOf(
        Color(0xFFffffff),
        Color(0xFFff80ed),
        Color(0xFFff0000),
        Color(0xFFFFA500),
        Color(0xFFFFD700),
        Color(0xFFfa8072),
        Color(0xFF20b2aa),
        Color(0xFF00ff7f),
        Color(0xFFcc0000),
        Color(0xFFff7f50),
    )
}
@Preview
@Composable
fun PrevLoginScreen() {
    NotesTheme {

        LoginScreen(onNavToHomePage = { /*TODO*/ }) {

        }
    }

}