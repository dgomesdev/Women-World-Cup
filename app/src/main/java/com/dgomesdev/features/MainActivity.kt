package com.dgomesdev.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.extensions.observe
import com.dgomesdev.notification_scheduler.extensions.NotificationMatchWorker
import com.dgomesdev.ui.theme.WomenWorldCupTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeActions()
        setContent {
                val mainState by mainViewModel.state.collectAsState()
                val favoritesState by favoritesViewModel.state.collectAsState()
                WomenWorldCupApp(
                    matches = mainState.matches,
                    favoriteMatches = favoritesState.favoriteMatches,
                    mainViewModel::toggleNotification,
                    mainViewModel::filterMatches,
                    favoritesViewModel::refreshFavoritesScreen,
                    favoritesViewModel::removeFromFavorites
                )
        }
    }

    @Composable
    fun WomenWorldCupApp(
        matches: List<MatchDomain>,
        favoriteMatches: List<MatchDomain>,
        onNotificationClick: NotificationOnClick,
        onFilterChosen: ChooseFilter,
        onRefreshFavoritesScreen: RefreshFavoritesScreen,
        removeFavorite: RemoveFromFavorite
    ) {
        WomenWorldCupTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            var filterButtonText by remember {
                mutableStateOf("All stages")
            }

            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                topBar = {
                    MatchTopBar(
                        onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                },
                drawerContent = {
                    MainMenuHeader()
                    MainMenuBody(
                        onFilterButtonTextChange = { filterButtonText = it },
                        onFilterSelected = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        })
                },
                bottomBar = {
                    MatchesBottomNavigation(
                        onScreenSelected = {
                            navController.navigate(it)
                        },
                        onRefreshFavoritesScreen = onRefreshFavoritesScreen
                    )
                }
            ) { padding ->
                MatchesNavHost(
                    navController = navController,
                    modifier = Modifier.padding(padding),
                    matches = matches,
                    favoriteMatches = favoriteMatches,
                    onNotificationClick = onNotificationClick,
                    onFilterChosen = onFilterChosen,
                    onRefreshFavoritesScreen = onRefreshFavoritesScreen,
                    removeFavorite = removeFavorite,
                    filterButtonText = filterButtonText
                )
            }
        }
    }

    private fun observeActions() {
        mainViewModel.action.observe(this) { action ->
            when (action) {
                is MainUiAction.MatchesNotFound -> TODO()
                is MainUiAction.Unexpected -> TODO()
                is MainUiAction.DisableNotification -> NotificationMatchWorker.cancel(
                    applicationContext,
                    action.match
                )
                is MainUiAction.EnableNotification -> NotificationMatchWorker.start(
                    applicationContext,
                    action.match
                )
            }
        }
        favoritesViewModel.action.observe(this) { action ->
            when (action) {
                is FavoritesUiAction.MatchesNotFound -> TODO()
                is FavoritesUiAction.Unexpected -> TODO()
                is FavoritesUiAction.DisableNotification -> NotificationMatchWorker.cancel(
                    applicationContext,
                    action.match
                )
            }
        }
    }
}