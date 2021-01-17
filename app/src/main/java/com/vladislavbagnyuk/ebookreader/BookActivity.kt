package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mertakdut.BookSection
import com.github.mertakdut.Reader
import com.github.mertakdut.exception.OutOfPagesException
import com.github.mertakdut.exception.ReadingException
import kotlinx.android.synthetic.main.activity_book.*


class BookActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")

    var pageCount = 0
    private var currentPage = 0

    private val reader = Reader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        bottomAppBar.post {
            bottomAppBar.performHide()
        }

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

        val ebookPath = intent.getStringExtra("ebookPath")
        val ebookTitle = intent.getStringExtra("ebookTitle")
        if (ebookTitle != null) {
            toolbar_title.text = ebookTitle
        }

        if (ebookPath !== null) {
            reader.setMaxContentPerSection(800) // Max string length for the current page.
            reader.setIsIncludingTextContent(true) // Optional, to return the tags-excluded version.
            reader.setFullContent(ebookPath) // Must call before readSection.
            pageCount = loadPageCount(this)
            renderPage()
        }

        // Set a SeekBar change listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentPage = ((i.toDouble() / 100 * pageCount).toInt())
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
    }

    private fun setLabels(percentage: Int) {
        seekBar.progress = percentage
        "$percentage%".also { percentageLabel.text = it }
        "$currentPage/$pageCount".also { pagesCountLabel.text = it }
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
        currentPage += 1
        renderPage()
    }

    fun prevPage(view: View?) {
        currentPage -= 1
        renderPage()
    }

    // Get total page count
    private fun loadPageCount(context: Context): Int {
        println("loadPageCount")
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        if (!sharedPreferences.contains("PAGE_NUM")) savePageCount(
            context,
            designatePageCount(context)
        )
        return sharedPreferences.getInt("PAGE_NUM", Int.MAX_VALUE)
    }

    private fun designatePageCount(context: Context): Int {
        println("designatePageCount")
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

    private fun savePageCount(context: Context, num: Int) {
        println("savePageCount")
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        sharedPreferences.edit().putInt("PAGE_NUM", num).apply()
    }
}

