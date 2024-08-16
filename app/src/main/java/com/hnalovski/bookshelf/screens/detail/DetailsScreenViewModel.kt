package com.hnalovski.bookshelf.screens.detail

import androidx.lifecycle.ViewModel
import com.hnalovski.bookshelf.data.DataOrException
import com.hnalovski.bookshelf.model.Item
import com.hnalovski.bookshelf.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        return repository.getBookInfo(bookId)
    }
}