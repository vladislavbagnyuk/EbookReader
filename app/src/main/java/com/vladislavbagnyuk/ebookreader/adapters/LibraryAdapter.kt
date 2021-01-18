package com.vladislavbagnyuk.ebookreader.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladislavbagnyuk.ebookreader.BookActivity
import com.vladislavbagnyuk.ebookreader.MainActivity
import com.vladislavbagnyuk.ebookreader.R
import com.vladislavbagnyuk.ebookreader.database.Book
import kotlinx.android.synthetic.main.book_card_library.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.processNextEventInCurrentThread


class LibraryAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<LibraryAdapter.BookViewHolder>() {

    class BookViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.book_card_library, parent, false)
        return BookViewHolder(view)

    }

    // Specifies the contents for the shown book card
    @InternalCoroutinesApi
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, index: Int) {
        val book = books[index]

        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, BookActivity::class.java)
            intent.putExtra("ebookPath", book.ebookPath)
            intent.putExtra("ebookTitle", book.title)
            intent.putExtra("ebookId", book.id)
            intent.putExtra("lastPage", book.lastPage)
            intent.putExtra("pages", book.pages)
            v.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener { v ->
            processNextEventInCurrentThread()
            MainActivity().selectedBook = 3
            Log.d("asdsad", book.author)
            v.showContextMenu()
        }

        with(holder.card) {
            if (book.cover != null) {
                val bmp = BitmapFactory.decodeByteArray(book.cover, 0, book.cover.size)
                iv_cover.setImageBitmap(bmp)
            } else {
                iv_cover.setImageResource(R.drawable.cover_not_available)
            }

            tv_percentage.text = "${book.percentage()}%"
        }

    }

    override fun getItemCount() = books.size

}