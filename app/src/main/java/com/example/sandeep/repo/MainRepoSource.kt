package com.example.sandeep.repo

import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepoSource {
   suspend fun getHomeTabData(page:Int, limit:Int): Flow<Resource<ArrayList<DetailData>>>
}