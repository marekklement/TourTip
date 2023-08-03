package cz.klement.model.param

import com.papsign.ktor.openapigen.annotations.parameters.PathParam
import java.util.UUID

data class IdParam(
  @PathParam("Identifier") val id: UUID
)
