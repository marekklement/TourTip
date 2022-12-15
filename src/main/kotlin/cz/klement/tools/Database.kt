package cz.klement.tools

import cz.klement.extensions.*
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
  val config = environment.config
  Database.connect(config.getDbUrl(), driver = config.getDbDriver(), user = config.getDbUser(), password = config.getDbUserPwd())
  val flyway = Flyway.configure()
    .dataSource(config.getDbUrl(), config.getDbUser(), config.getDbUserPwd())
    .locations(config.getFlywayDir())
    .load()
  transaction {
    flyway.migrate()
  }
}
