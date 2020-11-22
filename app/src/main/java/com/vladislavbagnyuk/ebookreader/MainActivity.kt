package com.vladislavbagnyuk.ebookreader

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        rv_recent.setHasFixedSize(true)
        rv_recent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_recent.adapter = RecentAdapter(getRecentBooks())

        rv_library.setHasFixedSize(true)
        rv_library.layoutManager = GridLayoutManager(this, 2)
        rv_library.adapter = LibraryAdapter(getSampleBooks())
    }
}

fun getSampleBooks(): List<Book> {
    return listOf(
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
            Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
            Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
            Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
            Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
            Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
            Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
    )
}

fun getRecentBooks(): List<Book> {
    return listOf(
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book("The Lord of the Rings: The Fellowship of the Ring", "J. R. R. Tolkien", R.drawable.coverlotr, 1200, 200, "Lorem ipsum dolor sit amet"),
        Book("Harry Potter and the Half-Blood Prince", "J. K. Rowling", R.drawable.cover_harry_potter, 500, 320, "Lorem ipsum dolor sit amet"),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),)
}