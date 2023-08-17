package cz.klement.plugins

import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.route
import cz.klement.constants.API_PREFIX
import cz.klement.routes.admin.settingsAdmin
import cz.klement.routes.admin.teamsAdmin
import cz.klement.routes.admin.tournamentAdmin
import cz.klement.routes.basic.users
import cz.klement.routes.admin.usersAdmin
import cz.klement.routes.auth.usersAuth
import io.ktor.server.application.*

fun Application.configureRouting() {

  apiRouting {
    route(API_PREFIX) {
      users()
      usersAuth()
      usersAdmin()
      teamsAdmin()
      tournamentAdmin()
      settingsAdmin()
    }

  }
}
