package cz.klement.mapper.command

import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.TournamentGameCreateCommand
import java.util.*

fun TournamentGameCreateCommand.mapCommand(tournamentId: UUID) =
  GameCreateCommand(
    tournamentId = tournamentId,
    homeTeamId = homeTeamId,
    awayTeamId = awayTeamId,
    startTime = startTime,
    homeScore = null,
    awayScore = null
  )
