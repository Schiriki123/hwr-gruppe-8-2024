package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.LoadGamesPort
import hwr.oop.group8.chess.persistence.SaveGamePort

class Cli(saveGamePort: SaveGamePort, loadGamesPort: LoadGamesPort) {
  val commands = listOf(
    NewGameCommand(saveGamePort),
    PrintGameCommand(loadGamesPort),
    MakeMoveCommand(loadGamesPort, saveGamePort),
    ListGamesCommand(loadGamesPort),
    HelpCommand(),
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    requireNotNull(command) { "No command found for arguments: $args" }
    command.handle(args)
  }
}
