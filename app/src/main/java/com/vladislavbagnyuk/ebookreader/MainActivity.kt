package com.vladislavbagnyuk.ebookreader

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mertakdut.Reader
import com.github.mertakdut.exception.OutOfPagesException
import com.github.mertakdut.exception.ReadingException
import com.vladislavbagnyuk.ebookreader.adapters.LibraryAdapter
import com.vladislavbagnyuk.ebookreader.adapters.RecentAdapter
import com.vladislavbagnyuk.ebookreader.database.Book
import com.vladislavbagnyuk.ebookreader.database.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private val CHOOSE_EBOOK_REQUEST = 1
    private lateinit var mBookViewModel: BookViewModel
    private val reader = Reader()
    var selectedBook = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))

        rv_recent.setHasFixedSize(true)
        rv_recent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_library.setHasFixedSize(true)
        rv_library.layoutManager = GridLayoutManager(this, 2)
        registerForContextMenu(rv_library)

        // room database
        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        mBookViewModel.getRecentsBooks.observe(this, Observer { book ->
            rv_recent.adapter = RecentAdapter(book)
        })
        mBookViewModel.getAllBooks.observe(this, Observer { book ->
            rv_library.adapter = LibraryAdapter(book)
        })
    }

    /**
     * MENU
     */
    override fun onCreateContextMenu(menu: ContextMenu?, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v.id == R.id.rv_library) {
            val inflater = menuInflater
            inflater.inflate(R.menu.library_menu_list, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return if (item.itemId == R.id.delete) {
            Log.d("asdsad", "xd")
            Log.d("asdsad", selectedBook.toString())
//            mBookViewModel.deleteBook(selectedBook)

            true
        } else {
            super.onContextItemSelected(item)
        }
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
            val toast = Toast.makeText(
                applicationContext,
                R.string.successfully_saved,
                Toast.LENGTH_SHORT
            )

            // start loading spinner animation
            progress_loader.visibility = View.VISIBLE;

            thread {
                val ebookPath = getFileFromUri(contentResolver, data.data!!, cacheDir).path

                reader.setMaxContentPerSection(610) // Max string length for the current page.
                reader.setIsIncludingTextContent(true) // Optional, to return the tags-excluded version.
                reader.setFullContent(ebookPath) // Must call before readSection.
                val pageCount = designatePageCount(this)
                val author = reader.infoPackage.metadata.creator
                val title = reader.infoPackage.metadata.title
                val coverImage = reader.coverImage

                // todo check if author is not null

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

                // stop loading spinner animation
                runOnUiThread {
                    progress_loader.visibility = View.GONE;
                }

                toast.show()
            }
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