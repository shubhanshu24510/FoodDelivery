package com.vipulasri.FoodDelivery.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipulasri.FoodDelivery.data.Repository
import com.vipulasri.FoodDelivery.data.Result
import com.vipulasri.FoodDelivery.network.model.Dashboard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _dashboardItems = MutableLiveData<Result<List<Dashboard.Item>>>()
    val dashboardItems: LiveData<Result<List<Dashboard.Item>>> = _dashboardItems

    fun loadData(showRandom: Boolean = false) {
        _dashboardItems.postValue(Result.Loading)
        viewModelScope.launch {
            delay(1000) // delay added to slow-down API request
            _dashboardItems.postValue(Repository.getDashboardData(showRandom))
        }
    }
}
