package cz.klement.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toZonedInstant(): Instant {
  return this.atZone(ZoneId.systemDefault()).toInstant()
}

fun Instant.toLocalDateTime(): LocalDateTime {
  return this.atZone(ZoneId.systemDefault()).toLocalDateTime()
}
