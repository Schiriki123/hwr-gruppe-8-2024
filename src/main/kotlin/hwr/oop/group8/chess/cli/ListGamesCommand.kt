package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.PersistencePort

class ListGamesCommand(private val persistencePort: PersistencePort) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 2) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("list", "games")

    return firstTwoArgsMatch
  }

  override fun handle(args: List<String>) {
    println("Loading all games...")
    val games = persistencePort.loadAllGames()
    println("List of games:")
    for (game in games) {
      println(
        "Game ID: ${game.id}, Current turn: ${game.getFenData().getTurn()}",
      )
    }
  }
}
