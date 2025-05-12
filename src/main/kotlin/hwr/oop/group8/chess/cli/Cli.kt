package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.InitGameInterface

class Cli(initGameInterface: InitGameInterface) {
  val commands = listOf(
    NewGameCommand(initGameInterface),
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args)}
    requireNotNull(command) {"No command found for arguments: $args"}
    command.handle(args)
  }
}
