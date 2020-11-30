package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        bottomAppBar.post {
            bottomAppBar.performHide()
        }

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            font_spinner.adapter = adapter
        }

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
}

