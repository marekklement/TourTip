package cz.klement.mapper.command

import cz.klement.extensions.convertNull
import cz.klement.extensions.map
import cz.klement.extensions.runMappingFlat
import cz.klement.extensions.safeMap
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentGameCreateCommand
import cz.klement.model.request.GameCreateRequest
import cz.klement.model.request.TeamImportRequest
import cz.klement.model.request.TournamentCreateRequest

fun TournamentCreateRequest.mapCommand() =
  runMappingFlat { errors ->
    TournamentCreateCommand(
      name = name.convertNull(errors = errors, TournamentCreateCommand::name.name),
      startDate = startDate.convertNull(errors = errors, TournamentCreateCommand::startDate.name),
      endDate = endDate.convertNull(errors = errors, TournamentCreateCommand::endDate.name),
      games = games.convertNull(errors = errors, fieldName = TournamentCreateCommand::games.name)
        .safeMap(errors = errors, fieldName = TeamImportRequest::teamNames.name, block = ::map).map { it.mapCommand() }
    )
  }

fun GameCreateRequest.mapCommand() =
  runMappingFlat { errors ->
    TournamentGameCreateCommand(
      homeTeamId = homeTeamId.convertNull(errors = errors, TournamentGameCreateCommand::homeTeamId.name),
      awayTeamId = awayTeamId.convertNull(errors = errors, TournamentGameCreateCommand::homeTeamId.name),
      startTime = startTime.convertNull(errors = errors, TournamentGameCreateCommand::homeTeamId.name)
    )
  }
