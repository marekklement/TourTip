package cz.klement.mapper.command

import cz.klement.exception.UnhandledMappingValidationException
import cz.klement.extensions.toPrettyString
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentGameCreateCommand
import cz.klement.model.request.GameCreateRequest
import cz.klement.model.request.TournamentCreateRequest
import io.ktor.server.plugins.*

fun TournamentCreateRequest.mapCommand() =
  checkValidity(this).run {
    TournamentCreateCommand(
      name = name ?: throw UnhandledMappingValidationException(),
      startDate = startDate ?: throw UnhandledMappingValidationException(),
      endDate = endDate ?: throw UnhandledMappingValidationException(),
      games = games.map { it.mapCommand() }
    )
  }

fun GameCreateRequest.mapCommand() =
  checkValidity(this).run {
    TournamentGameCreateCommand(
      homeTeamId = homeTeamId ?: throw UnhandledMappingValidationException(),
      awayTeamId = awayTeamId ?: throw UnhandledMappingValidationException(),
      startTime = startTime ?: throw UnhandledMappingValidationException()
    )
  }

private fun checkValidity(request: TournamentCreateRequest) {
  mutableListOf<String>().apply {
    if (request.name.isNullOrEmpty()) add(request::name.name)
    if (request.startDate.toString().isEmpty()) add(request::startDate.name)
    if (request.endDate.toString().isEmpty()) add(request::endDate.name)
  }.run {
    if (isNotEmpty()) throw BadRequestException("${toPrettyString()} fields are mandatory. Please provide it.")
  }
}

private fun checkValidity(request: GameCreateRequest) {
  mutableListOf<String>().apply {
    if (request.homeTeamId == null) add(GameCreateRequest::homeTeamId.name)
    if (request.awayTeamId == null) add(GameCreateRequest::awayTeamId.name)
    if (request.startTime.toString().isEmpty()) add(GameCreateRequest::startTime.name)
  }.run {
    if (isNotEmpty()) throw BadRequestException("${toPrettyString()} fields are mandatory. Please provide it.")
  }
}
