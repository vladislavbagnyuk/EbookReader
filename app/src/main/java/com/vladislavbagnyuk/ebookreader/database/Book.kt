package com.vladislavbagnyuk.ebookreader.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val cover: ByteArray,
    val pages: Int,
    val ebookPath: String,
    val lastPage: Int = 0,
    val timestamp: Int = System.currentTimeMillis().toInt()
) {
    fun percentage(): Int {
        return ((lastPage.toDouble() / pages) * 100).toInt()
    }
}