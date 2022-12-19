package cz.klement.extensions

import io.ktor.server.config.*

fun ApplicationConfig.getDbUrl() = property("database.url").getString()
fun ApplicationConfig.getDbDriver() = property("database.driver").getString()
fun ApplicationConfig.getDbUser() = property("database.user").getString()
fun ApplicationConfig.getDbUserPwd() = property("database.password").getString()
fun ApplicationConfig.getFlywayDir() = property("database.flyway-mig-repo").getString()
fun ApplicationConfig.getJwtSecret() = property("jwt.secret").getString()
fun ApplicationConfig.getJwtIssuer() = property("jwt.issuer").getString()
fun ApplicationConfig.getJwtAudience() = property("jwt.audience").getString()
fun ApplicationConfig.getJwtRealm() = property("jwt.realm").getString()
