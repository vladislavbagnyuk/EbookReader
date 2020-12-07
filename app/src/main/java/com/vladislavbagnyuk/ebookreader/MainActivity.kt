package com.vladislavbagnyuk.ebookreader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private val CHOOSE_EBOOK_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))

        rv_recent.setHasFixedSize(true)
        rv_recent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_recent.adapter = RecentAdapter(getRecentBooks())

        rv_library.setHasFixedSize(true)
        rv_library.layoutManager = GridLayoutManager(this, 2)
        rv_library.adapter = LibraryAdapter(getSampleBooks())
    }

    fun openBook(view: View) {
        val intent = Intent(this, BookActivity::class.java)
        startActivity(intent)
    }

    fun addBook(view: View) {
        val intent = Intent()
        intent.type = "application/epub+zip"
        intent.action = Intent.ACTION_GET_CONTENT
        val chooser = Intent.createChooser(intent, "Choose ebook file")
        startActivityForResult(chooser, CHOOSE_EBOOK_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_EBOOK_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            Log.d(TAG, "An ebook was chosen by the user")
            Log.d(TAG, data.data.toString())
        }
    }
}


fun getSampleBooks(): List<Book> {
    return listOf(
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book(
            "The Lord of the Rings: The Fellowship of the Ring",
            "J. R. R. Tolkien",
            R.drawable.coverlotr,
            1200,
            200,
            "Lorem ipsum dolor sit amet"
        ),
        Book(
            "Harry Potter and the Half-Blood Prince",
            "J. K. Rowling",
            R.drawable.cover_harry_potter,
            500,
            320,
            "Lorem ipsum dolor sit amet"
        ),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book(
            "The Lord of the Rings: The Fellowship of the Ring",
            "J. R. R. Tolkien",
            R.drawable.coverlotr,
            1200,
            200,
            "Lorem ipsum dolor sit amet"
        ),
        Book(
            "Harry Potter and the Half-Blood Prince",
            "J. K. Rowling",
            R.drawable.cover_harry_potter,
            500,
            320,
            "Lorem ipsum dolor sit amet"
        ),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book(
            "The Lord of the Rings: The Fellowship of the Ring",
            "J. R. R. Tolkien",
            R.drawable.coverlotr,
            1200,
            200,
            "Lorem ipsum dolor sit amet"
        ),
        Book(
            "Harry Potter and the Half-Blood Prince",
            "J. K. Rowling",
            R.drawable.cover_harry_potter,
            500,
            320,
            "Lorem ipsum dolor sit amet"
        ),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book(
            "The Lord of the Rings: The Fellowship of the Ring",
            "J. R. R. Tolkien",
            R.drawable.coverlotr,
            1200,
            200,
            "Lorem ipsum dolor sit amet"
        ),
        Book(
            "Harry Potter and the Half-Blood Prince",
            "J. K. Rowling",
            R.drawable.cover_harry_potter,
            500,
            320,
            "Lorem ipsum dolor sit amet"
        ),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
    )
}

fun getRecentBooks(): List<Book> {
    return listOf(
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
        Book(
            "The Lord of the Rings: The Fellowship of the Ring",
            "J. R. R. Tolkien",
            R.drawable.coverlotr,
            1200,
            200,
            "Lorem ipsum dolor sit amet"
        ),
        Book(
            "Harry Potter and the Half-Blood Prince",
            "J. K. Rowling",
            R.drawable.cover_harry_potter,
            500,
            320,
            "Lorem ipsum dolor sit amet"
        ),
        Book("1984", "George Orwell", R.drawable.cover1984, 860, 320, "Lorem ipsum dolor sit amet"),
    )
}