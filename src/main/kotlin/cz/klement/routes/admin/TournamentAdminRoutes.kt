package cz.klement.routes.admin

import cz.klement.mapper.command.mapCommand
import cz.klement.model.request.TournamentCreateRequest
import cz.klement.routes.TOURNAMENT_PREFIX
import cz.klement.service.api.TournamentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.tournamentAdmin() {

  val tournamentService by closestDI().instance<TournamentService>()

  post(TOURNAMENT_PREFIX) {
    call.receive<TournamentCreateRequest>().mapCommand()
      .run(tournamentService::create)
      .also {
        call.respond(HttpStatusCode.Accepted)
      }
  }

}
