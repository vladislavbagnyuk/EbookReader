package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mertakdut.BookSection
import com.github.mertakdut.Reader
import com.github.mertakdut.exception.OutOfPagesException
import com.github.mertakdut.exception.ReadingException
import com.vladislavbagnyuk.ebookreader.database.BookViewModel
import kotlinx.android.synthetic.main.activity_book.*


class BookActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")

    var pageCount = 0

    private var currentPage = 1
    private var ebookId = 0;

    private val reader = Reader()
    private lateinit var mBookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        // Hide UI on book open
        bottomAppBar.post {
            bottomAppBar.performHide()
        }

        // room Db
        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        // Swipe listener
        contentLayout.setOnTouchListener(object : OnSwipeTouchListener(this@BookActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                nextPage(null)
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                prevPage(null)
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                showControls(null)
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                showControls(null)
            }
        })

        // Load book
        val ebookPath = intent.getStringExtra("ebookPath")
        val ebookTitle = intent.getStringExtra("ebookTitle")
        ebookId = intent.getIntExtra("ebookId", 0)
        currentPage = intent.getIntExtra("lastPage", 0)
        pageCount = intent.getIntExtra("pages", 0)

        if (ebookTitle != null) {
            toolbar_title.text = ebookTitle
        }

        if (ebookPath !== null) {
            reader.setMaxContentPerSection(610) // Max string length for the current page.
            reader.setIsIncludingTextContent(true) // Optional, to return the tags-excluded version.
            reader.setFullContent(ebookPath) // Must call before readSection.
            renderPage()
        }

        // Set a SeekBar change listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentPage = ((i.toDouble() / 100 * pageCount).toInt())
                if (currentPage >= pageCount) {
                    currentPage = pageCount - 1
                }
                setLabels(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                renderPage()
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun renderPage() {
        val bookSection: BookSection = reader.readSection(currentPage)
        val sectionContent = bookSection.sectionContent // Returns content as html.
        val sectionTextContent = bookSection.sectionTextContent
        val percentage = ((currentPage.toDouble() / pageCount) * 100).toInt()
        setLabels(percentage)

        // set html to webview
        contentPanel.text = sectionTextContent

        if (ebookId > 0) {
            // update current page in db
            mBookViewModel.updateCurrentPageById(ebookId, currentPage)
        } else {
            Toast.makeText(this, R.string.unable_to_save_progress_to_database, Toast.LENGTH_SHORT)
                .show()
        }

        // todo - ziskani coveru (chyba knihovny)
        // todo - zrychleni recyclerview (komprese obrazku coveru)
        // todo - recents (5 s nejvyssim timestampem) (+ poresit XML resource viz minuly commit)
        // todo - loading spinner pri pridavani knihy
        // todo vymazat pocitani lastpage v bookactivity
        // todo vymazat Book.kt
    }

    private fun setLabels(percentage: Int) {
        seekBar.progress = percentage
        val displayPage = currentPage + 1
        "$percentage%".also { percentageLabel.text = it }
        "$displayPage/$pageCount".also { pagesCountLabel.text = it }
    }

    fun showControls(view: View?) {
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

    fun nextPage(view: View?) {
        if (currentPage < pageCount - 1) {
            currentPage += 1
            renderPage()
        }
    }

    fun prevPage(view: View?) {
        if (currentPage > 0) {
            currentPage -= 1
            renderPage()
        }
    }
}

