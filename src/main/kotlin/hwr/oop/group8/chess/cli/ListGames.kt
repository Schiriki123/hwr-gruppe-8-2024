package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.group8.chess.persistence.PersistencePort

class ListGames(private val port: PersistencePort) : CliktCommand() {
  override fun run() {
    echo("Loading all games...")
    val games = port.loadAllGames()
    echo("List of games:")
    for (game in games) {
      echo("Game ID: ${game.id}, Current turn: ${game.fen().getTurn()}")
    }
  }
}
