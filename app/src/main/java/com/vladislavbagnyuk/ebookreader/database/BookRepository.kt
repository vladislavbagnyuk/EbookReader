package com.vladislavbagnyuk.ebookreader.database

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val getAllBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    fun updateCurrentPageById(id: Int, currentPage: Int) {
        bookDao.updateCurrentPageById(id, currentPage)
    }
}