package com.vladislavbagnyuk.ebookreader

data class Book(val title: String, val author: String, val cover: Int, val pages: Int, val lastPage: Int, val content: String) {
    fun percentage(): Int {
        return ((lastPage.toDouble() / pages) * 100).toInt()
    }
}