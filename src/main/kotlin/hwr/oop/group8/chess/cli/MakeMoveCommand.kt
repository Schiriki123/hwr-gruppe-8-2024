package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.PersistencePort

class MakeMoveCommand(private val persistencePort: PersistencePort) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size < 5 || args.size > 7) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("make", "move")
    val thirdArgIsNumber = args[2].toIntOrNull() != null
    val fourthArgIsValid = args[3].matches(Regex("[a-h][1-8]"))
    val fifthArgIsValid = args[4].matches(Regex("[a-h][1-8]"))
    val sixthArgIsValid =
      (args.size == 6 && args[5].matches(Regex("[qbnr]"))) || args.size == 5

    return firstTwoArgsMatch &&
      thirdArgIsNumber &&
      fourthArgIsValid &&
      fifthArgIsValid &&
      sixthArgIsValid
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    val from = Position.fromString(args[3])
    val to = Position.fromString(args[4])
    val promotionCharacter: Char? =
      if (args.size == 6) args[5].first() else null

    val cliMove =
      CliMove(from, to, promotionCharacter)
    val game = persistencePort.loadGame(gameId)
    game.makeMove(cliMove)
    persistencePort.saveGame(game, true)

    println("Move made from $from to $to.")
  }
}
