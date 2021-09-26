package com.example.core.di

import com.example.core.util.ErrorMessages
import com.example.core.util.Resource
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Sandeep Sankla
 */

class FormattedResponse @Inject constructor(private val apiInterface: ApiInterface) {
  // @Inject lateinit var appLog: AppLog

    public suspend fun <T> GetCall(apiUrl: String, queryMap: Map<String, Any>?, ):Resource<T>{
        return getResult(apiInterface.get(apiUrl, queryMap))
    }

   private  fun <T> getResult(result: Response<T>?, e: Exception? = null): Resource<T> {
        if (result != null && result.isSuccessful) {
            result.body()?.let {
               /* val gson  = Gson().toJson(it)
                val datads = Gson().fromJson(gson, DetailData::)*/
                   return Resource.Success(it)
            } ?: run {
                return Resource.Error( ErrorMessages(-1).getMessage(), -1)
            }
        } else {
            e?.let {
                return Resource.Error( it.message  ?: ErrorMessages(-1).getMessage(), -1)
            } ?: kotlin.run {
                val errorResponse = result?.errorBody()?.string()
                val errorCode =result?.raw()?.code
                return Resource.Error( errorResponse ?: ErrorMessages(errorCode).getMessage(), errorCode)
            }
        }
   }
}