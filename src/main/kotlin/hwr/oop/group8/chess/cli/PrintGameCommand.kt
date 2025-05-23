package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.LoadGameInterface

class PrintGameCommand(private val loadGameInterface: LoadGameInterface) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 3) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("show", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    println("Loading game with id $gameId...")
    println("Current board:")
    printBoard(loadGameInterface.loadGame(gameId).board)
    println(
      "Current turn: ${
        loadGameInterface.loadGame(gameId).getFenData().getTurn()
      }"
    )
  }

  private fun printBoard(board: Board) {
    val builder = StringBuilder()
    for (rank in 8 downTo 1) {
      for (file in 'a'..'h') {
        val piece = board.getMap().getValue(Position(file, rank)).getPiece()
        builder.append(piece?.getChar() ?: '.')
      }
      builder.append("${System.lineSeparator()}")
    }
    println(builder.toString().trim())
  }
}

