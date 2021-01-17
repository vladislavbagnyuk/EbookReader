package com.vladislavbagnyuk.ebookreader.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    /*suspend */fun addBook(book: Book)

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>
/*
    @Query("SELECT * FROM books WHERE id=:bookId")
    fun getBookById(bookId: Int): Book

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
 */
}