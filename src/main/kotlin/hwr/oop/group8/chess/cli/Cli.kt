package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.PersistencePort

class Cli(persistencePort: PersistencePort) {
  val commands = listOf(
    NewGameCommand(persistencePort),
    ShowGameCommand(persistencePort),
    MakeMoveCommand(persistencePort),
    ListGamesCommand(persistencePort),
    DeleteGameCommand(persistencePort),
    HelpCommand(),
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    requireNotNull(command) { "No command found for arguments: $args" }
    command.handle(args)
  }
}
