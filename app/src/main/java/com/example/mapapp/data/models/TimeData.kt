package com.example.mapapp.data.models

import android.view.View
import android.widget.Toast
import java.util.*

class TimeData(
    val uri: String,
    val title: String,
    val content: String,
    val date: Date
){
    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Clicked: $title", Toast.LENGTH_SHORT).show()
    }
}