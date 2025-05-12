package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.InitGameInterface

class NewGameCommand(private val persistenceAdapter: InitGameInterface) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("new", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    try {
      persistenceAdapter.initGame(gameId)
      println("New game with id $gameId created.")
    } catch (e: Exception) {
      println("Error creating new game: ${e.message}")
    }
  }
}
