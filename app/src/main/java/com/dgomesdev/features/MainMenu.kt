package com.dgomesdev.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun MainMenuBody(
    modifier: Modifier = Modifier,
    onItemClick: (filterType: String, filter: String) -> Unit
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick("group", "GROUP F")
                }
                .padding(16.dp)
        ) {
            Text(
                text = "Groups",
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick("stadium", "HINDMARSH STADIUM")
                }
                .padding(16.dp)
        ) {
            Text(
                text = "Stadiums",
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick("team", "BRA")
                }
                .padding(16.dp)
        ) {
            Text(
                text = "Teams",
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}