package com.example.mapapp.data.models

import java.time.Year

class DateYM (
    var year: Int = 0,
    var month: Int = 0
){

    fun setDateYM(year: Int, month: Int){
        this.year = year
        this.month = month
    }
}