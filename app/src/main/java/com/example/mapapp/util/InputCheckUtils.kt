package com.example.mapapp.util

import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern


object InputCheckUtils {

    fun setErrorText(view: TextInputLayout, isNotOk: Boolean, message: String) : Boolean{

        return if(isNotOk){
            view.isErrorEnabled = true
            view.error = message
            false
        }else{
            view.isErrorEnabled = false
            true
        }

    }

    fun isEmail(email: String): Boolean {
        return if(email.isNotEmpty()){
            var returnValue = false
            val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
            val p: Pattern = Pattern.compile(regex)
            val m: Matcher = p.matcher(email)
            if (m.matches()) {
                returnValue = true
            }
            returnValue
        }else{
            false
        }
    }

    fun isValidPW(pw: String): Boolean{
        return return pw.length >=6
    }

}