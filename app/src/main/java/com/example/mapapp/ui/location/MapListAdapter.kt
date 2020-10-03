package com.example.mapapp.ui.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.data.models.LocalMapData

class MapListAdapter (mapList : MutableList<LocalMapData.Document>) : RecyclerView.Adapter<MapListAdapter.MapDataViewHolder>() {

    private var mapList :  MutableList<LocalMapData.Document> = mapList
    private lateinit var context: Context

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapDataViewHolder {

        context = parent!!.context

        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.row_item_map_list,parent,false)
        return MapDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapDataViewHolder, position: Int) {

        var data = mapList!![position]
        //holder!!.imageView.setImageResource(photoUri)
        holder!!.title_text.text = data.place_name
        holder!!.category_text.text = data.category_name
        holder!!.address_road_text.text = data.road_address_name
        holder!!.address_text.text = data.address_name

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

    }

    override fun getItemCount(): Int {
        return mapList!!.size
    }


    class MapDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var address_road_text = view.findViewById(R.id.map_road_address) as TextView
        var title_text = view.findViewById(R.id.map_title) as TextView
        var category_text = view.findViewById(R.id.map_category_text) as TextView
        var address_text = view.findViewById(R.id.map_address) as TextView

    }
}
