package cz.klement.constants

const val API_PREFIX = "/api/v1"

const val USERS_PREFIX = "/users"
const val USERS_LOGIN = "$USERS_PREFIX/login"
const val USERS_BY_ID = "$USERS_PREFIX/{id}"
const val USERS_BY_ID_PWD_CHANGE = "$USERS_BY_ID/pwd-change"

const val TOURNAMENT_PREFIX = "/tournament"

const val SETTINGS_PREFIX = "/settings"
const val SETTINGS_BY_ID = "$SETTINGS_PREFIX/{id}"

const val TEAMS_PREFIX = "/teams"
const val TEAMS_BY_ID = "$TEAMS_PREFIX/{id}"
