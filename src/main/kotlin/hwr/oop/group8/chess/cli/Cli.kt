package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.InitGameInterface
import hwr.oop.group8.chess.persistence.LoadAllGamesInterface
import hwr.oop.group8.chess.persistence.LoadGameInterface
import hwr.oop.group8.chess.persistence.SaveGameInterface

class Cli(
  initGameInterface: InitGameInterface,
  loadGameInterface: LoadGameInterface,
  saveGameInterface: SaveGameInterface,
  loadAllGamesInterface: LoadAllGamesInterface
) {
  val commands = listOf(
    NewGameCommand(initGameInterface),
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
