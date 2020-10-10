package com.example.mapapp.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.data.models.TimeData
import com.example.mapapp.ui.time.TimeRcAdapter

object DatabindingUtils {
    @BindingAdapter("bind_timeList")
    @JvmStatic
    fun bindTimeList(recyclerView: RecyclerView, items: ObservableArrayList<TimeData>){
        if(recyclerView.adapter == null){
            val lm = LinearLayoutManager(recyclerView.context)
            val adapter = TimeRcAdapter()
            recyclerView.layoutManager = lm
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as TimeRcAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
    }

}