package com.vipulasri.FoodDelivery.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import com.vipulasri.FoodDelivery.components.AppTopBar
import com.vipulasri.FoodDelivery.components.ShowError
import com.vipulasri.FoodDelivery.components.ShowLoading
import com.vipulasri.FoodDelivery.data.Result
import com.vipulasri.FoodDelivery.ui.JetDeliveryTheme
import com.vipulasri.FoodDelivery.ui.dashboard.ShowDashboard

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContent {
            JetDeliveryApp(viewModel)
        }
    }
}

@Composable
fun JetDeliveryApp(viewModel: MainViewModel) {
    var showRandom by remember { mutableStateOf(false) }

    viewModel.loadData(showRandom)

    JetDeliveryTheme {
        Scaffold(
            topBar = {
                AppTopBar(
                    name = stringResource(id = R.string.app_name),
                    showRandom = showRandom,
                    onShowRandomDashboardChange = {
                        showRandom = it
                    }
                )
            }
        ) {
            when (val data = viewModel.dashboardItems.observeAsState().value) {
                is Result.Loading -> {
                    ShowLoading()
                }
                is Result.Success -> {
                    ShowDashboard(
                        data = data.data ?: emptyList()
                    )
                }
                is Result.Failure -> {
                    ShowError(
                        message = data.error.message ?: "",
                        onRetry = {
                            viewModel.loadData(showRandom)
                        }
                    )
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}
