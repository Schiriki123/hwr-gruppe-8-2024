package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
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
    val from =
      Position(
        File.fromChar(args[3].first()),
        Rank.fromInt(args[3].last().digitToInt()),
      )
    val to = Position(
      File.fromChar(args[4].first()),
      Rank.fromInt(args[4].last().digitToInt()),
    )
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
