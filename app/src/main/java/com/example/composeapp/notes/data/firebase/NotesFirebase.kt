package com.example.composeapp.notes.data.firebase

import android.util.Log
import com.example.composeapp.notes.model.Notes
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object NotesFirebase {

    private const val TAG = "NotesFirebase"

    fun addNote(note: Notes) {
        Firebase.firestore.collection("Notes").add(note)
            .addOnSuccessListener {
                Log.d(TAG, "addNote: ${it.id}")
            }
            .addOnFailureListener {
                Log.e(TAG, "addNote: ${it.message}")
            }
    }

    fun deleteNote(note: Notes) {
        Firebase.firestore.collection("Notes").where(
            Filter.and(
                Filter.equalTo("title", note.title),
                Filter.equalTo("description", note.description)
            )
        ).get().addOnCompleteListener {
            if (it.isComplete){
                it.result?.documents?.get(0)?.reference?.delete()?.addOnSuccessListener {
                    Log.d(TAG, "deleteNote: ${note.title}")
                }?.addOnFailureListener {
                    Log.e(TAG, "deleteNote: ",it )
                }
            }
        }
        }

    }