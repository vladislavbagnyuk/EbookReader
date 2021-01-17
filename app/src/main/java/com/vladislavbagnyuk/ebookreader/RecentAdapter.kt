package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladislavbagnyuk.ebookreader.database.Book
import kotlinx.android.synthetic.main.book_card_library.view.*
import kotlinx.android.synthetic.main.book_card_library.view.iv_cover
import kotlinx.android.synthetic.main.book_card_library.view.tv_percentage
import kotlinx.android.synthetic.main.book_card_recent.view.*

class RecentAdapter(private val books: List<Book>) :
        RecyclerView.Adapter<RecentAdapter.BookViewHolder>() {

    class BookViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card_recent, parent, false)
        return BookViewHolder(view)
    }

    // Specifies the contents for the shown book card
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, index: Int) {
        val book = books[index]
        val percentage = book.percentage()

        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, BookActivity::class.java)
            intent.putExtra("ebookPath", book.ebookPath)
            intent.putExtra("ebookTitle", book.title)
            intent.putExtra("ebookId", book.id)
            intent.putExtra("lastPage", book.lastPage)
            intent.putExtra("pages", book.pages)
            v.context.startActivity(intent)
        }

        with(holder.card) {
            val bmp = BitmapFactory.decodeByteArray(book.cover, 0, book.cover.size)
            iv_cover.setImageBitmap(bmp)
            tv_percentage.text = "${percentage}%"
            progress_bar.progress = percentage
        }
    }

    override fun getItemCount() = books.size

}