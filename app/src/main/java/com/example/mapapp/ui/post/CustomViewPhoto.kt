package com.example.mapapp.ui.post

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.mapapp.R
import com.example.mapapp.util.CameraUtil
import kotlinx.android.synthetic.main.custom_view_add_photo.view.*
import java.util.*

/*
총 4개까지 추가 가능하며
4개째 추가하면 추가 버튼이 gone되고
그 이전것들의 삭제 버튼을 누르면 삭제된다

 */

open class CustomViewPhoto @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : LinearLayout(context, attrs, defStyleAttr){

    private val btnList: ArrayList<ImageView> = arrayListOf()

    private var clickListener: ButtonClickListener? = null

    interface ButtonClickListener {
        fun onClick(i: Int)
    }

    open fun setReportListener(listener: ButtonClickListener) {
        clickListener = listener
    }

    init{
        inflate(context, R.layout.custom_view_add_photo, this)
        btnList.add(img)

    }


    open fun addBtn(uri: Uri) {

            //4개 이하면 추가
            val createdButton = ImageButton(context)
             CameraUtil.getInstance(context).setImageView(createdButton, uri)
            val params = LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 5, 0)
            createdButton.layoutParams = params
            createdButton.setOnClickListener { v -> selectedBtn(v) }
            btnList.add(createdButton)
            layout.addView(createdButton)

    }

     open fun selectedBtn(v: View) {
        for (i in btnList.indices) {
            if (btnList[i].id == v.id) {
                clickListener!!.onClick(i)
            } else {
                btnList[i].isSelected = false
            }
        }
    }


}
