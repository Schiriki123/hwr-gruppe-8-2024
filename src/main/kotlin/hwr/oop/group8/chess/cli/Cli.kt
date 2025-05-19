package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.LoadAllGamesInterface
import hwr.oop.group8.chess.persistence.LoadGameInterface
import hwr.oop.group8.chess.persistence.SaveGameInterface

class Cli(
  loadGameInterface: LoadGameInterface,
  saveGameInterface: SaveGameInterface,
  loadAllGamesInterface: LoadAllGamesInterface
) {
  val commands = listOf(
    NewGameCommand(saveGameInterface),
    PrintGameCommand(loadGameInterface),
    MakeMoveCommand(loadGameInterface, saveGameInterface),
    ListGamesCommand(loadAllGamesInterface),
    HelpCommand()
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    requireNotNull(command) { "No command found for arguments: $args" }
    command.handle(args)
  }
}
