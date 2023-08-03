package cz.klement.plugins

import com.papsign.ktor.openapigen.OpenAPIGen
import io.ktor.server.application.*

fun Application.configureOpenApi() {
  install(OpenAPIGen) {
    // this servers OpenAPI definition on /openapi.json
    serveOpenApiJson = true
    // this servers Swagger UI on /swagger-ui/index.html
    serveSwaggerUi = true
    info {
      title = "Minimal Example API"
    }
  }
}
