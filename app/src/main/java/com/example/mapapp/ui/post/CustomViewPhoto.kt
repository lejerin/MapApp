package com.example.mapapp.ui.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.mapapp.R
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
        fun onClick(i: Int, v: View)
    }

    open fun setReportListener(listener: ButtonClickListener) {
        clickListener = listener
    }

    init{
        inflate(context, R.layout.custom_view_add_photo, this)
        addBtn()
    }


    open fun addBtn() {


            layout.post{
                val createdButton = AppCompatImageView(context)
                createdButton.id = btnList.size + 100
                val width = (layout.measuredWidth - 40) / 4
                System.out.println("추가" + width)
                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                params.width = width
                params.setMargins(5, 0, 5, 0)
                createdButton.layoutParams = params
                createdButton.scaleType = ImageView.ScaleType.CENTER_CROP
                createdButton.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24)
                createdButton.setBackgroundResource(R.drawable.rounded_gray_box)
                createdButton.clipToOutline = true
                createdButton.setOnClickListener { v -> selectedBtn(v) }
                btnList.add(createdButton)
                layout.addView(createdButton)
            }


    }

     open fun selectedBtn(v: View) {
        for (i in btnList.indices) {
            if (btnList[i].id == v.id) {
                System.out.println("클릭" + i)
                clickListener!!.onClick(i, btnList[i])
            }
        }
    }

    open fun getImgSize() = btnList.size

}
