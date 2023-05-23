package com.dgomesdev.domain.model

import java.time.LocalDateTime

typealias MatchDomain = Match

data class Match(
    val id: String,
    val name: String,
    val stadium: Stadium,
    val homeTeam: Team,
    val awayTeam: Team,
    val date: LocalDateTime,
    val isNotificationEnabled: Boolean = false
)