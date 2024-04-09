package com.example.notes.repository

import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    val currentUser:FirebaseUser? = Firebase.auth.currentUser
}