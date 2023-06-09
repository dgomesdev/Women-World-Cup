package com.dgomesdev.features

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dgomesdev.domain.extensions.getDate
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.model.TeamDomain
import com.dgomesdev.ui.theme.Shapes
import com.dgomesdev.women_world_cup.R
import kotlinx.coroutines.launch

typealias NotificationOnClick = (match: MatchDomain) -> Unit
typealias FilterOnClick = (filterType: String, filter: String) -> Unit

@Composable
fun MainScreen(
    matches: List<MatchDomain>,
    onNotificationClick: NotificationOnClick,
    filterOnClick: FilterOnClick
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
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
            MainMenuBody(onItemClick = filterOnClick)
        },
        bottomBar = { MatchesBottomNavigation() },
    ) { padding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(16.dp))
            FilterChoice()
            Spacer(modifier = Modifier.size(16.dp))
            MatchesList(
                Modifier.padding(padding),
                matches = matches,
                onNotificationClick = onNotificationClick
            )
        }

    }
}

@Composable
private fun MatchTopBar(
    onNavigationIconClick: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(
                "Women's World Cup 2023"
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { Toast.makeText(context, "Info", Toast.LENGTH_SHORT).show() }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Localized description"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun FilterChoice() {
    var expanded by remember { mutableStateOf(false) }
    var buttonText by rememberSaveable { mutableStateOf("All matches") }
    FilterButton(
        expanded = expanded,
        onExpandedChange = { expanded = it},
        buttonText = buttonText,
        onTextChange = { buttonText = it}
    )
}

@Composable
fun FilterButton(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    buttonText: String,
    onTextChange: (String) -> Unit
) {
    Column {
        ElevatedButton(onClick = { onExpandedChange(true)}) {
            Text(text = buttonText)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            DropdownMenuItem(
                text = { Text("Filter matches") },
                onClick = {
                    onTextChange("Filtered Matches")
                    onExpandedChange(false)
                })
        }
    }
}

@Composable
fun MatchesList(
    modifier: Modifier = Modifier,
    matches: List<MatchDomain>,
    onNotificationClick: NotificationOnClick
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(matches) { match ->
                MatchInfo(match, onNotificationClick)
            }
        }
    }
}

@Composable
fun MatchInfo(match: MatchDomain, onNotificationClick: NotificationOnClick) {
    Card(
        shape = Shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            AsyncImage(
                model = match.stadium.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(160.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Notification(match, onNotificationClick)
                Title(match)
                Teams(match)
            }
        }
    }
}

@Composable
fun Notification(match: MatchDomain, onClick: NotificationOnClick) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        val drawable = if (match.isNotificationEnabled) R.drawable.ic_notifications_active
        else R.drawable.ic_notifications

        Image(
            painter = painterResource(id = drawable),
            modifier = Modifier.clickable {
                onClick(match)
            },
            contentDescription = null
        )
    }
}

@Composable
fun Title(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${match.date.getDate()} - ${match.name}",
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )
    }
}

@Composable
fun Teams(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamItem(team = match.homeTeam)

        Text(
            text = "X",
            modifier = Modifier.padding(end = 16.dp, start = 16.dp),
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )

        TeamItem(team = match.awayTeam)
    }
}

@Composable
fun TeamItem(team: TeamDomain) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = team.flag,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h3.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = team.displayName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )
    }
}

@Composable
private fun MatchesBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("Home")
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = {
                Text("Favorites")
            },
            selected = false,
            onClick = {}
        )
    }
}