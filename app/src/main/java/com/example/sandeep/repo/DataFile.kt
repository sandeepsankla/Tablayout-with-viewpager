package com.example.sandeep.repo

import androidx.fragment.app.Fragment
import com.google.gson.annotations.SerializedName


 data class FragmentNames(
    @SerializedName("name")val name:String,
    @SerializedName("fragment") val fragment: Fragment
)


/*

data class DetailDataResponse(
    val dataList: List<DetailData>
)*/


data class DetailData(
    @SerializedName("name")val name:String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_checked") var isChecked: Boolean?,
    @SerializedName("id") val id: Double?
)

data class DetailResponse(
    val data:ArrayList<DetailData>
)