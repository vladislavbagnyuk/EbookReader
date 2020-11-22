package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_card.view.*

class BooksAdapter(private val books: List<Book>) :
        RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    class BookViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card, parent, false)
        return BookViewHolder(view)
    }

    // Specifies the contents for the shown book card
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, index: Int) {
        val book = books[index]
        with(holder.card) {
            iv_cover.setImageResource(book.cover)
            tv_percentage.text = "${book.percentage()}%";
        }

    }

    override fun getItemCount() = books.size

}