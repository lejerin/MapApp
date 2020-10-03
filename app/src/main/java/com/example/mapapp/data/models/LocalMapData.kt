package com.example.mapapp.data.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class LocalMapData (

    @SerializedName("documents")
    var documents: List<Document>


){
    data class Document (

        @SerializedName("address_name") val address_name : String,
        @SerializedName("category_group_code") val category_group_code : String,
        @SerializedName("category_group_name") val category_group_name : String,
        @SerializedName("category_name") val category_name : String,
        @SerializedName("distance") val distance : String,
        @SerializedName("phone") val phone : String,
        @SerializedName("place_name") val place_name : String,
        @SerializedName("place_url") val place_url : String,
        @SerializedName("road_address_name") val road_address_name : String,
        @SerializedName("x") val x : String,
        @SerializedName("y") val y : String
    )

}