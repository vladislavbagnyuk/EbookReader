package com.vladislavbagnyuk.ebookreader

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        bottomAppBar.post {
            bottomAppBar.performHide()
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

