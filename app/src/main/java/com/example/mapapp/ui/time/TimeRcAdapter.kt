package com.example.mapapp.ui.time

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.data.models.TimeData
import com.example.mapapp.databinding.RowItemTimeRcBinding

class TimeRcAdapter : RecyclerView.Adapter<TimeRcAdapter.ViewHolder>(){
    var items = ArrayList<TimeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemTimeRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(binding:RowItemTimeRcBinding):RecyclerView.ViewHolder(binding.root){
        val binding = binding
        fun bind(model:TimeData){
            binding.time = model
        }
    }
}