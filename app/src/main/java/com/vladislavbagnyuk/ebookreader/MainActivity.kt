package com.vladislavbagnyuk.ebookreader

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mertakdut.Reader
import com.github.mertakdut.exception.OutOfPagesException
import com.github.mertakdut.exception.ReadingException
import com.vladislavbagnyuk.ebookreader.database.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private val CHOOSE_EBOOK_REQUEST = 1
    private lateinit var mBookViewModel: BookViewModel
    private val reader = Reader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))

        rv_recent.setHasFixedSize(true)
        rv_recent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_recent.adapter = RecentAdapter(getRecentBooks())

        rv_library.setHasFixedSize(true)
        rv_library.layoutManager = GridLayoutManager(this, 2)

        // room database
        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        mBookViewModel.getAllBooks.observe(this, Observer { book ->
            rv_library.adapter = LibraryAdapter(book)
        })
    }

    fun openBook(view: View) {
        // todo del this method ?
/*
        val intent = Intent(this, BookActivity::class.java)
        intent.putExtra("ebookPath", ebookPath)
        startActivity(intent)*/
    }

    fun addBook(view: View) {
        val intent = Intent()
        intent.type = "application/epub+zip"
        intent.action = Intent.ACTION_GET_CONTENT
        val chooser = Intent.createChooser(intent, "Choose ebook file")
        startActivityForResult(chooser, CHOOSE_EBOOK_REQUEST)
    }

    private fun getFileFromUri(contentResolver: ContentResolver, uri: Uri, directory: File): File {
        val file = File.createTempFile("suffix", ".prefix", directory)
        file.outputStream().use {
            contentResolver.openInputStream(uri)?.copyTo(it)
        }

        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_EBOOK_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            Log.d(TAG, "An ebook was chosen by the user")

            val ebookPath = getFileFromUri(contentResolver, data.data!!, cacheDir).path

            reader.setMaxContentPerSection(610) // Max string length for the current page.
            reader.setIsIncludingTextContent(true) // Optional, to return the tags-excluded version.
            reader.setFullContent(ebookPath) // Must call before readSection.
            val pageCount = designatePageCount(this)
            val author = reader.infoPackage.metadata.creator
            val title = reader.infoPackage.metadata.title
            var coverImage = reader.coverImage

            // todo check if author or title vals are not null...

            if (coverImage == null) {
                // save default cover image
                val largeIcon: Bitmap =
                    BitmapFactory.decodeResource(resources, R.drawable.cover_not_available)

                val stream = ByteArrayOutputStream()
                largeIcon.compress(Bitmap.CompressFormat.JPEG, 10, stream)
                val bitmapdata = stream.toByteArray()

                coverImage = bitmapdata
            }

            // save ebookpath to db
            val book = com.vladislavbagnyuk.ebookreader.database.Book(
                0,
                title,
                author,
                coverImage,
                pageCount,
                ebookPath
            )
            mBookViewModel.addBook(book)

            Toast.makeText(applicationContext, R.string.successfully_saved, Toast.LENGTH_SHORT).show()
        }
    }

    // Get total page count
    private fun designatePageCount(context: Context): Int {
        try {
            reader.readSection(Int.MAX_VALUE)
        } catch (e: ReadingException) {
            e.printStackTrace()
        } catch (e: OutOfPagesException) {
            e.printStackTrace()
            return e.pageCount
        }
        return Int.MAX_VALUE
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