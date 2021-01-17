package com.vladislavbagnyuk.ebookreader.database

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val getAllBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    fun addBook(book: Book) {
        bookDao.addBook(book)
    }
}