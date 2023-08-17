package cz.klement.plugins

import cz.klement.service.api.*
import cz.klement.service.impl.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun DI.MainBuilder.initInjections() {
  bind<UserService>() with singleton { UserServiceImpl() }
  bind<GameService>() with singleton { GameServiceImpl() }
  bind<WinnerService>() with singleton { WinnerServiceImpl() }
  bind<TeamService>() with singleton { TeamServiceImpl() }
  bind<SettingsKeyValueService>() with singleton { SettingsKeyValueServiceImpl() }
  bind<PredictionService>() with singleton { PredictionServiceImpl() }
  bind<TournamentService>() with singleton { TournamentServiceImpl(instance()) }
}
