package com.dgomesdev.features

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable

@Composable
fun FilterMatchesByStage(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onTextButtonChange: (String) -> Unit,
    onFilterChosen: (String, String) -> Unit,
    stages: List<String>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        for(stage in stages) {
            DropdownMenuItem(
                text = { Text(stage) },
                onClick = {
                    onTextButtonChange(stage)
                    onFilterChosen("stage", stage.uppercase())
                    onExpandChange(false)
                }
            )
        }
    }
}

@Composable
fun FilterMatchesByStadium(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onTextButtonChange: (String) -> Unit,
    onFilterChosen: (String, String) -> Unit,
    stadiums: List<String>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        for(stadium in stadiums) {
            DropdownMenuItem(
                text = { Text(stadium) },
                onClick = {
                    onTextButtonChange(stadium)
                    onFilterChosen("stadium", stadium.uppercase())
                    onExpandChange(false)
                }
            )
        }
    }
}

@Composable
fun FilterMatchesByTeam(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onTextButtonChange: (String) -> Unit,
    onFilterChosen: (String, String) -> Unit,
    countries: Map<String, String>
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        for(country in countries.entries.iterator()) {
            DropdownMenuItem(
                text = { Text(country.key) },
                onClick = {
                    onTextButtonChange(country.key)
                    onFilterChosen("team", country.value.uppercase())
                    onExpandChange(false)
                }
            )
        }

    }
}