package com.example.sandeep

import androidx.lifecycle.*
import com.example.core.base.BaseViewModel
import com.example.sandeep.repo.DetailData
import com.example.core.util.Resource
import com.example.sandeep.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val repository : MainRepository): BaseViewModel() {
    var mList : ArrayList<DetailData> = arrayListOf()
    var PAGE =1

    private var _tabData = MutableLiveData<Resource<ArrayList<DetailData>>>()
    val tabData: LiveData<Resource<ArrayList<DetailData>>> = _tabData


    fun getData(page:Int, limit:Int){
        viewModelScope.launch {
            repository.getHomeTabData(page, limit).collect {
                _tabData.value = it
            }
        }
    }
}