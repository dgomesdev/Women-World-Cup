package com.dgomesdev.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.getDate() : String = DateTimeFormatter.ofPattern("dd/MM HH:mm").format(this)