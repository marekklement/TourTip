package cz.klement.routes.admin

import com.papsign.ktor.openapigen.route.application
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.auth.delete
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.auth.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tags
import cz.klement.enums.SwaggerTags
import cz.klement.mapper.command.mapCommand
import cz.klement.model.request.TournamentCreateRequest
import cz.klement.plugins.adminTokenAuthorized
import cz.klement.constants.TOURNAMENT_PREFIX
import cz.klement.model.param.IdParam
import cz.klement.service.api.TournamentService
import io.ktor.http.*
import io.ktor.server.auth.jwt.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun NormalOpenAPIRoute.tournamentAdmin() {
  with(application) {
    val tournamentService by closestDI().instance<TournamentService>()

    adminTokenAuthorized {
      route(TOURNAMENT_PREFIX) {
        post<Unit, HttpStatusCode, TournamentCreateRequest, JWTPrincipal> (
          info(
            summary = "Create new tournament"
          ),
          tags(SwaggerTags.TOURNAMENT)
        ){ _, request ->
          request.mapCommand().run(tournamentService::create)
            .also {
              respond(HttpStatusCode.Created)
            }
        }
      }

      delete<IdParam, HttpStatusCode, JWTPrincipal> (
        info(
          summary = "Delete tournament"
        ),
        tags(SwaggerTags.TOURNAMENT)
      ){ params ->
        tournamentService.delete(params.id)
        respond(HttpStatusCode.NoContent)
      }
    }
  }

}
