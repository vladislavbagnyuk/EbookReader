package com.vladislavbagnyuk.ebookreader

data class Book(val title: String, val author: String, val cover: Int, val pages: Int, val lastPage: Int, val content: String) {
    fun percentage() : Int {
        return ((lastPage.toDouble() / pages) * 100).toInt()
    }
}

fun getSampleHabits(): List<Book> {
    return listOf(
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
        Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet")
    )
}