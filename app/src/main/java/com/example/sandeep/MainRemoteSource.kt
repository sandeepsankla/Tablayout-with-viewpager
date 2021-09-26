package com.example.sandeep

import com.example.core.di.FormattedResponse
import com.example.sandeep.repo.DetailData
import com.example.sandeep.repo.DetailResponse
import com.example.core.util.Resource
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by Sandeep Sankla
 */
class MainRemoteSource @Inject constructor(val formattedResponse: FormattedResponse)   {


    suspend fun getHomeTabData(page: Int, limit: Int): Resource<ArrayList<DetailData>> {
        val url = "https://api.punkapi.com/v2/beers"
        val map = hashMapOf<String, Int>()
        map.put("page", page)
        map.put("per_page", limit)
        val response :Resource<ArrayList<DetailData>> = formattedResponse.GetCall(url, map)
        val gson  = Gson().toJson(response)
        val playerArray = Gson().fromJson(gson, DetailResponse::class.java)
        return Resource.Success(playerArray.data)
    }


}