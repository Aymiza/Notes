package com.example.notes.repository

import com.example.notes.models.Notes
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val NOTES_COLLECTION_REF ="notes"

class StorageRepository (){
    val user = Firebase.auth.currentUser
    fun hasUser (): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val  notesRef: CollectionReference =Firebase
        .firestore.collection(NOTES_COLLECTION_REF)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserNotes(
        userId: String
    ): Flow<com.example.notes.repository.Resources <List<Notes>>> = callbackFlow {
        var snapshotStateListener:ListenerRegistration? = null

        try {
            snapshotStateListener = notesRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null){
                        val notes = snapshot.toObjects(Notes::class.java)
                        com.example.notes.repository.Resources.Success (data = notes)
                    }else {
                        com.example.notes.repository.Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        }catch (e: Exception){

            trySend(com.example.notes.repository.Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }

    }

    fun getNote(
        noteId: String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Notes) -> Unit
    ){
        notesRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener{result ->
                onError.invoke(result.cause)
            }
    }

    fun addNotes(
        userId: String,
        title: String,
        description:String,
        timestamp: Timestamp,
        color: Int = 0,
        onComplete: (Boolean) -> Unit,
    ){
        val documentId = notesRef.document().id
        val note = Notes(userId, title, description,timestamp,color,
        documentId=documentId)
        notesRef.document(documentId)
            .set(note)
            .addOnCompleteListener { result->
                onComplete.invoke(result.isSuccessful)
            }
    }


    fun deleteNote(noteId: String, onComplete: (Boolean) -> Unit){
        notesRef.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }
    fun updateNote(
        title: String,
        note: String,
        color: Int,
        noteId: String,
        onResult:(Boolean) -> Unit
    ){
        val updateData = hashMapOf<String, Any>(
            "colorIndex" to color,
            "description" to note,
            "title" to title
        )
        notesRef.document(noteId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}

private fun Any.invoke(notes: Notes?): DocumentSnapshot? {
    TODO("Not yet implemented")
}


sealed class Resources<T>(
    val data : T? =null,
    val throwable: Throwable? = null,
) {
   class Loading<T>: com.example.notes.repository.Resources<T>()
    class Success<T>(data: T?):com.example.notes.repository.Resources<T>(data = data)

    class Error<T>(throwable: Throwable?): com.example.notes.repository.Resources<T>(throwable=throwable)

}