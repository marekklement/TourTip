package cz.klement.model.command

import java.time.Instant

data class TournamentSearchCommand(
  val name: String?,
  val dateTimeSpanStart: Instant?,
  val dateTimeSpanEnd: Instant?,
  val page: Long,
  val size: Int?
)
