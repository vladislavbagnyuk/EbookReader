package com.vladislavbagnyuk.ebookreader.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book: Book)

    @Query("SELECT * FROM books ORDER BY id DESC")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books ORDER BY timestamp DESC LIMIT 5")
    fun getRecentsBooks(): LiveData<List<Book>>

    @Update
    fun updateBook(book: Book)

    @Query("UPDATE books SET lastPage = :currentPage, timestamp = :timestamp WHERE id = :id")
    fun updateCurrentPageById(id: Int, currentPage: Int, timestamp: Int = System.currentTimeMillis().toInt())

    @Delete
    fun deleteBook(book: Book)
}