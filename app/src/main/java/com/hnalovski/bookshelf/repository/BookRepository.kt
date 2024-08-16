package com.hnalovski.bookshelf.repository

import com.hnalovski.bookshelf.data.DataOrException
import com.hnalovski.bookshelf.model.Item
import com.hnalovski.bookshelf.network.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BookApi) {

    suspend fun getBooks(query: String): DataOrException<List<Item>, Boolean, Exception> {
        val result = DataOrException<List<Item>, Boolean, Exception>()
        result.loading = true

        try {
            val itemList = api.getAllBooks(query = query)
            result.data = itemList.items
            result.loading = false
        } catch (e: Exception) {
            result.e = e
            result.loading = false
        }

        return result
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val result = DataOrException<Item, Boolean, Exception>()
        result.loading = true

        try {
            val item = api.getBookInfo(bookId)
            result.data = item
            result.loading = false
        } catch (e: Exception) {
            result.e = e
            result.loading = false
        }

        return result
    }

}