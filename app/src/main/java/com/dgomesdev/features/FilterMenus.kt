package com.dgomesdev.features

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable

@Composable
fun FilterMatchesByStage() {
    DropdownMenu(expanded = false, onDismissRequest = { }) {
        DropdownMenuItem(
            text = { Text("Group A") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group B") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group C") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group D") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group E") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group F") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group G") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Group H") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Round of 16") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Quarter finals") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Semi finals") },
            onClick = {

            })
        DropdownMenuItem(
            text = { Text("Finals") },
            onClick = {

            })
    }
}

@Composable
fun FilterMatchesByStadium() {
    DropdownMenu(expanded = false, onDismissRequest = { }) {
        DropdownMenuItem(
            text = { Text("Group A") },
            onClick = {

            })
    }
}

@Composable
fun FilterMatchesByTeam() {
    DropdownMenu(expanded = false, onDismissRequest = { }) {
        DropdownMenuItem(
            text = { Text("Group A") },
            onClick = {

            })
    }
}