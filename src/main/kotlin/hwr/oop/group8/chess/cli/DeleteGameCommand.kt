package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.PersistencePort

class DeleteGameCommand(private val persistencePort: PersistencePort) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 3) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("delete", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    persistencePort.deleteGame(gameId)
    println("Game with ID $gameId deleted successfully.")
  }
}
