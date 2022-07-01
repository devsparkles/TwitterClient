package com.devsparkle.twitterclient.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devsparkle.twitterclient.utils.ConnectionLiveData

abstract class BaseActivity : AppCompatActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
    }


    fun showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(baseContext, message, duration).show()
    }

}