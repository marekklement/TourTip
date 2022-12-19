package cz.klement.tools

import cz.klement.service.api.UserService
import cz.klement.service.impl.UserServiceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun DI.MainBuilder.initInjections() {
  bind<UserService>() with singleton { UserServiceImpl() }
}
