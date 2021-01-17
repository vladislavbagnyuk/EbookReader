package com.vladislavbagnyuk.ebookreader.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val getAllBooks : LiveData<List<Book>>
    val getRecentsBooks : LiveData<List<Book>>
    private val repository : BookRepository

    init {
        val bookDao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
        getAllBooks = repository.getAllBooks
        getRecentsBooks = repository.getRecentsBooks
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun updateCurrentPageById(id: Int, currentPage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentPageById(id, currentPage)
        }
    }
}