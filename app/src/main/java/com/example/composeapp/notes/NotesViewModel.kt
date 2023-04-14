package com.example.composeapp.notes


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.notes.data.firebase.NotesFirebase
import com.example.composeapp.notes.data.repository.NoteRepository
import com.example.composeapp.notes.data.retrofit.NotesAPI
import com.example.composeapp.notes.model.EmbeddingRequest
import com.example.composeapp.notes.model.EmbeddingResponse
import com.example.composeapp.notes.model.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    @Inject
    lateinit var notesAPI: NotesAPI

    var notes = mutableStateListOf<Notes>()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun getNotes(): List<Notes> {

        repository.getNotes().onEach {
            notes.clear()
            notes.addAll(it)
        }.launchIn(viewModelScope)

        return notes
    }

    suspend fun getNotesById(id: Int): Notes {
        return repository.getNotesById(id)
    }

    suspend fun addNotes(note: Notes) {
        repository.addNote(note)

        textToEmbeddings(note.title + " " + note.description, embedding = {
            note.embeddings = it

            NotesFirebase.addNote(note)

            editNote(note)
        })
    }

    suspend fun deleteNote(note: Notes) {
        repository.deleteNote(note)

        NotesFirebase.deleteNote(note)
    }

    suspend fun editNote(note: Notes) {
        repository.addNote(note)
    }

    suspend fun textToEmbeddings(
        inputText: String,
        embedding: suspend (output: List<Float>) -> Unit
    ) {

        withContext(Dispatchers.IO){
            val call: Call<EmbeddingResponse?>? =
                notesAPI.getEmbedding(EmbeddingRequest(input = inputText))

            val response: Response<EmbeddingResponse?> = call!!.execute()

            if (response.isSuccessful) {

                val output: List<Float> = response.body()!!.data[0].embedding

                embedding.invoke(output)

            } else {
                // Handle the API error
            }
        }

    }
}
