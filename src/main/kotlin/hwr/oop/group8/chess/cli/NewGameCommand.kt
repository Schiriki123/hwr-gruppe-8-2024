package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.InitGameInterface

class NewGameCommand(private val initGameInterface: InitGameInterface) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 3) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("new", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    initGameInterface.initGame(gameId)
    println("New game with id $gameId created.")
  }
}
