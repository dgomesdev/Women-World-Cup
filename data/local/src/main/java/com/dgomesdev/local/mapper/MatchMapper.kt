package com.dgomesdev.local.mapper

import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.model.StadiumDomain
import com.dgomesdev.domain.model.TeamDomain
import com.dgomesdev.local.entity.MatchEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun MatchDomain.toEntity() = MatchEntity(
        id = "$homeTeam-$awayTeam",
        name = name,
        homeTeam = homeTeam.toTeamString(),
        awayTeam = awayTeam.toTeamString(),
        stadium = stadium.toStadiumString(),
        date = date.toDateString(),
        isNotificationEnabled = isNotificationEnabled
    )

internal fun List<MatchEntity>.toDomain() = map { it.toDomain() }
fun MatchEntity.toDomain(): MatchDomain {
    return MatchDomain(
        id = "$homeTeam-$awayTeam",
        name = name,
        homeTeam = homeTeam.toTeamDomain(),
        awayTeam = awayTeam.toTeamDomain(),
        stadium = stadium.toStadiumDomain(),
        date = date.toLocalDateTime(),
        isNotificationEnabled = isNotificationEnabled
    )
}

private fun StadiumDomain.toStadiumString() = "${this.name}|${this.image}"

private fun TeamDomain.toTeamString() = "${this.flag}|${this.displayName}"

private fun LocalDateTime.toDateString() = this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss][xxx]"))
private fun String.toTeamDomain(): TeamDomain {
    return TeamDomain(
        flag = this.substringBefore("|"),
        displayName = this.substringAfter("|")
    )
}

private fun String.toStadiumDomain(): StadiumDomain {
    return StadiumDomain(
        name = this.substringBefore("|"),
        image = this.substringAfter("|")
    )
}
private fun String.toLocalDateTime() = LocalDateTime.parse(
    this,
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss][xxx]")
)