package com.devsparkle.twitterclient.utils.extensions

import android.app.Activity
import android.widget.Toast


fun Activity.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(baseContext, message, duration).show()
}
