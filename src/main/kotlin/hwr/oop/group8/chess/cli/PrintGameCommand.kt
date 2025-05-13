package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.LoadGameInterface

class PrintGameCommand(private val loadGameInterface: LoadGameInterface) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("show", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    println("Loading game with id $gameId...")
    println("Current board:")
    loadGameInterface.loadGame(gameId).printBoard()
    println("Current turn: ${loadGameInterface.loadGame(gameId).getFenData().getTurn()}")
  }
}
