package cz.klement.extensions

import io.ktor.server.config.*

fun ApplicationConfig.getDbUrl() = property("database.url").getString()
fun ApplicationConfig.getDbDriver() = property("database.driver").getString()
fun ApplicationConfig.getDbUser() = property("database.user").getString()
fun ApplicationConfig.getDbUserPwd() = property("database.password").getString()
fun ApplicationConfig.getFlywayDir() = property("database.flyway-mig-repo").getString()
