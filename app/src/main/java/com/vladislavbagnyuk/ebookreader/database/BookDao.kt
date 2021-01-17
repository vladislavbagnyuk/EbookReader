package com.vladislavbagnyuk.ebookreader.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book: Book)

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    @Update
    fun updateBook(book: Book)

    @Query("UPDATE books SET lastPage = :currentPage WHERE id = :id")
    fun updateCurrentPageById(id: Int, currentPage: Int)
/*
    @Query("SELECT * FROM books WHERE id=:bookId")
    fun getBookById(bookId: Int): Book

    @Delete
    fun delete(book: Book)
 */
}