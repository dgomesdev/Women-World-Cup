package com.dgomesdev.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dgomesdev.domain.model.StadiumDomain
import com.dgomesdev.domain.model.TeamDomain
import java.time.LocalDateTime

typealias MatchEntity = Match
@Entity(tableName = "match_table")
data class Match (
    @PrimaryKey val id: String,
    val name: String,
    val stadium: String,
    val homeTeam: String,
    val awayTeam: String,
    val date: String,
    val isNotificationEnabled: Boolean
)