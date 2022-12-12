package cz.klement.tools

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun configureDatabase() {
  val flyway = Flyway.configure()
    .dataSource("jdbc:postgresql://localhost:5432/tipdb", "tipdb", "tipdb")
    .locations("filesystem:src/main/resources/db/migration")
    .load()
  flyway.migrate()

  Database.connect("jdbc:postgresql://localhost:5432/tipdb", driver = "org.postgresql.Driver", user = "tipdb", password = "tipdb")
}
