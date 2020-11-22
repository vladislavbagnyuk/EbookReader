package com.vladislavbagnyuk.ebookreader

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        rv_library.setHasFixedSize(true)
        rv_library.layoutManager = GridLayoutManager(this, 2);
        rv_library.adapter = BooksAdapter(getSampleBooks())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
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