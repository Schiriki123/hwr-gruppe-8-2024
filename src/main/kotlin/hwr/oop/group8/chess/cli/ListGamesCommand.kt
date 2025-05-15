package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.LoadAllGamesInterface

class ListGamesCommand(private val loadAllGamesInterface: LoadAllGamesInterface): CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 2) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("list", "games")

    return firstTwoArgsMatch
  }

  override fun handle(args: List<String>) {
    println("Loading all games...")
    val games = loadAllGamesInterface.loadAllGames()
    println("List of games:")
    for (game in games) {
      println("Game ID: ${game.id}, Current turn: ${game.getFenData().getTurn()}")
    }
  }
}
