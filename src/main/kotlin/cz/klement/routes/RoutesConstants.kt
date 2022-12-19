package cz.klement.routes

const val API_PREFIX = "/api/v1"

const val USERS_PREFIX = "/users"
const val USERS_LOGIN = "$USERS_PREFIX/login"
const val USERS_BY_ID = "$USERS_PREFIX/{id}"
const val USERS_BY_ID_PWD_CHANGE = "$USERS_BY_ID/pwd-change"
