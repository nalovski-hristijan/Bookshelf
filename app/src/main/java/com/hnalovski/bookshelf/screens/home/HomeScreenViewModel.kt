package com.hnalovski.bookshelf.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hnalovski.bookshelf.model.Item
import com.hnalovski.bookshelf.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)


    init {
        loadBooks()
    }

    fun loadBooks() {
        searchBooks("Android")
    }


    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                return@launch
            }

            try {
                when(val response = repository.getBooks(query)) {
                    else -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isLoading = false
                    }
                }
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }
}


