val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val psqlDriverVersion: String by project
val flywayVersion: String by project
val kodeinVersion: String by project
val cryptoVersion: String by project
val swaggerForstVersion: String by project
val jacksonDataType: String by project
val objenesisVersion: String by project
val loggingVersion: String by project

plugins {
  application
  kotlin("jvm") version "1.7.22"
  id("io.ktor.plugin") version "2.2.1"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.7.22"
}

group = "cz.klement"
version = "0.0.1"
application {
  mainClass.set("cz.klement.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-cors:$ktorVersion")
  implementation("io.ktor:ktor-server-auth:$ktorVersion")
  implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
  implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
  implementation("dev.forst:ktor-openapi-generator:0.6.1")
  implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
  implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
  implementation("org.postgresql:postgresql:$psqlDriverVersion")
  implementation("org.flywaydb:flyway-core:$flywayVersion")
  implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinVersion")
  implementation("org.mindrot:jbcrypt:$cryptoVersion")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDataType")
  api("org.objenesis:objenesis:$objenesisVersion")
  api("io.github.microutils:kotlin-logging-jvm:$loggingVersion")
}
