package com.dgomesdev.remote.mapper

import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.model.StadiumDomain
import com.dgomesdev.domain.model.TeamDomain
import com.dgomesdev.remote.model.MatchRemote
import com.dgomesdev.remote.model.StadiumRemote
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

internal fun List<MatchRemote>.toDomain() = map { it.toDomain() }

fun MatchRemote.toDomain(): MatchDomain {
    return MatchDomain(
        id = "$homeTeam-$awayTeam",
        name = name,
        homeTeam = homeTeam.toTeam(),
        awayTeam = awayTeam.toTeam(),
        stadium = stadium.toDomain(),
        date = date.toLocalDateTime(),
    )
}

private fun Date.toLocalDateTime(): LocalDateTime {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}

private fun String.toTeam(): TeamDomain {
    return TeamDomain(
        flag = getTeamFlag(this),
        displayName = Locale("", this).isO3Country
    )
}

private fun getTeamFlag(team: String): String {
    return team.map {
        String(Character.toChars(it.code + 127397))
    }.joinToString("")
}

fun StadiumRemote.toDomain(): StadiumDomain {
    return StadiumDomain(
        name = name,
        image = image
    )
}