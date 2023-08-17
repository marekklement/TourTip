package cz.klement.enums

import com.papsign.ktor.openapigen.APITag

enum class SwaggerTags(override val description: String): APITag {
  USERS("Users related endpoints"),
  USERS_AUTH("Users authentication endpoints"),
  TOURNAMENT("Tournaments related endpoints"),
  SETTINGS("Settings related endpoints"),
  TEAMS("Teams related endpoints"),
}
