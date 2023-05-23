package com.dgomesdev.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dgomesdev.extensions.observe
import com.dgomesdev.notification_scheduler.extensions.NotificationMatchWorker
import com.dgomesdev.ui.theme.WomenWorldCupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeActions()
        setContent {
            WomenWorldCupTheme {
                val state by viewModel.state.collectAsState()
                MainScreen(matches = state.matches, viewModel::toggleNotification)
            }
        }
    }

    private fun observeActions() {
        viewModel.action.observe(this) {action ->
            when (action) {
                is MainUiAction.MatchesNotFound -> TODO()
                MainUiAction.Unexpected -> TODO()
                is MainUiAction.DisableNotification -> NotificationMatchWorker.cancel(applicationContext, action.match)
                is MainUiAction.EnableNotification -> NotificationMatchWorker.start(applicationContext, action.match)
            }
        }
    }
}

