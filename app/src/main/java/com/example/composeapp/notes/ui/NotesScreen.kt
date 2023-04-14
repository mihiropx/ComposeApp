package com.example.composeapp.notes.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.notes.NotesViewModel
import com.example.composeapp.notes.model.Notes
import com.example.composeapp.notes.navigation.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlin.math.sqrt

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NotesScreen(navController = rememberNavController())
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(navController: NavHostController) {

    val viewModel: NotesViewModel = hiltViewModel()

    val searchText by viewModel.searchText.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                navController.navigate(Screen.AddNote.route) {
                    launchSingleTop = true
                    restoreState = true
                }

            }) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column() {
            Box() {
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search Note") })
                IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                    compareEmbeddings(searchText, viewModel)
                }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search Note")
                }
            }
            LazyColumn {
                items(viewModel.getNotes()) { note ->
                    NoteItemView(note, navController)
                }
            }
        }

    }

}

fun compareEmbeddings(searchText: String, viewModel: NotesViewModel) {

    GlobalScope.launch(Dispatchers.IO) {

        viewModel.textToEmbeddings(searchText, embedding = { embeddings ->

            Firebase.firestore.collection("Notes").get()
                .addOnSuccessListener {
                    if (it?.isEmpty == false) {
                        val savedEmbeddings = mutableListOf<FloatArray>()

                        val noteList = mutableListOf<Notes>()

                        for (i in 0 until it.size()) {
                            val note = it.documents[i].toObject(Notes::class.java)

                            note?.embeddings?.toFloatArray()?.let { savedEmbeddings.add(it) }

                            note?.let { noteList.add(it) }

                            // Calculate the cosine similarity between the saved embeddings and the input embeddings


                        }

                        val similarities =
                            savedEmbeddings.map {
                                cosineSimilarity(
                                    it,
                                    embeddings.toFloatArray()
                                )
                            }

                        // Retrieve the most similar word
                        val mostSimilarWordEntity =
                            noteList[similarities.indexOf(similarities.maxOrNull())]
                        val mostSimilarWord = mostSimilarWordEntity.title
//
//// Retrieve the most similar sentence
//                        val sentenceSimilarities = mutableListOf<Float>()
//                        val sentenceEmbeddings = mutableListOf<FloatArray>()
//                        val sentences = searchText.split(".")
//                        for (sentence in sentences) {
//                            val words = sentence.trim().split(" ")
//                            val sentenceEmbedding = words.mapNotNull { word ->
//                                embeddingDao.getEmbedding(word)?.embedding
//                            }.flatten().toFloatArray()
//                            if (sentenceEmbedding.isNotEmpty()) {
//                                sentenceEmbeddings.add(sentenceEmbedding)
//                                val sentenceSimilarity =
//                                    cosineSimilarity(sentenceEmbedding, savedEmbeddings)
//                                sentenceSimilarities.add(sentenceSimilarity)
//                            }
//                        }
//                        val mostSimilarSentence =
//                            sentences[sentenceSimilarities.indexOf(sentenceSimilarities.maxOrNull())]

                        Log.d("Embeddings", "compareEmbeddings: $mostSimilarWord")

                    }
                }
                .addOnFailureListener {
                    Log.e("NotesScreen", "compareEmbeddings: ", it)
                }

        })

    }


}

fun cosineSimilarity(x: FloatArray, y: FloatArray): Float {
    require(x.size == y.size) { "Input vectors must have the same dimension." }

    var dotProduct = 0f
    var normX = 0f
    var normY = 0f

    for (i in x.indices) {
        dotProduct += x[i] * y[i]
        normX += x[i] * x[i]
        normY += y[i] * y[i]
    }

    val denominator = sqrt(normX) * sqrt(normY)
    return if (denominator != 0f) {
        dotProduct / denominator
    } else {
        0f
    }
}

@Composable
fun NoteItemView(note: Notes, navController: NavHostController) {

    val viewModel: NotesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable {
                    val noteJson = Gson().toJson(note)
                    navController.navigate(Screen.NoteDetails.withStringArgs(noteJson))
                }
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
            ) {
                Text(
                    text = note.title ?: "",
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = note.description ?: "",
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            IconButton(content = {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Note"
                )
            },
                onClick = {
                    coroutineScope.launch {
                        viewModel.deleteNote(note)
                    }
                }
            )
        }

        Divider()
    }

}