package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.FEN
import hwr.oop.group8.chess.persistence.PersistencePort

class NewGameCommand(private val persistencePort: PersistencePort) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 3) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("new", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    val initialFEN = FEN()
    val initialGame = Game(gameId, initialFEN)
    persistencePort.saveGame(initialGame, false)
    println("New game with id $gameId created.")
  }
}
