package com.example.sandeep.repo

import com.example.core.util.Resource
import com.example.sandeep.MainRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainRemoteSource: MainRemoteSource) :
    MainRepoSource {

 override suspend fun getHomeTabData( page:Int, limit:Int): Flow<Resource<ArrayList<DetailData>>> {
        return flow {
            emit(Resource.Loading(true))
            emit(mainRemoteSource.getHomeTabData(page, limit))
        }.flowOn(Dispatchers.IO)
    }

}