package com.vladislavbagnyuk.ebookreader.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val cover: Int,
    val pages: Int,
    val lastPage: Int,
    val ebookPath: String
) {
    fun percentage(): Int {
        return ((lastPage.toDouble() / pages) * 100).toInt()
    }
}