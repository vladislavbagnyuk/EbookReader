package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.github.mertakdut.BookSection
import com.github.mertakdut.Reader
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")

    var currentPage = 1;
    private val reader = Reader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        bottomAppBar.post {
            bottomAppBar.performHide()
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.planets_array,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            //font_spinner.adapter = adapter
//        }

        val ebookPath = intent.getStringExtra("ebookPath")
        if (ebookPath !== null) {
            reader.setMaxContentPerSection(800) // Max string length for the current page.
            reader.setIsIncludingTextContent(true) // Optional, to return the tags-excluded version.
            reader.setFullContent(ebookPath) // Must call before readSection.
            renderPage()
        }

    }

    private fun renderPage() {
        val bookSection: BookSection = reader.readSection(currentPage)
        val sectionContent = bookSection.sectionContent // Returns content as html.
        val sectionTextContent = bookSection.sectionTextContent

        // set html to webview
        contentPanel.text = sectionTextContent
    }

    fun showControls(view: View) {
        if (toolbar.visibility == View.GONE) {
            bottomAppBar.performShow()
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
            bottomAppBar.performHide()
        }
    }

    fun back(view: View) {
        finish()
    }

    fun nextPage(view: View) {
        currentPage += 1
        renderPage()
    }

    fun prevPage(view: View) {
        currentPage -= 1
        renderPage()
    }
}

