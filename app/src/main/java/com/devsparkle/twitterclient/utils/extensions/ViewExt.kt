package com.devsparkle.twitterclient.utils.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/**
 * Remove the view if [condition] returns true
 * (visibility = View.GONE)
 */
inline fun View.hideIf(condition: () -> Boolean): View {
    if (visibility != View.GONE && condition()) {
        visibility = View.GONE
    }
    return this
}


fun Activity.hideKeyboard() {
    currentFocus?.let { view ->
        val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
