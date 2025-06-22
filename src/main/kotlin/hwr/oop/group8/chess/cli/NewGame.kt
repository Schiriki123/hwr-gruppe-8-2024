package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.FEN
import hwr.oop.group8.chess.persistence.PersistencePort

class NewGame(private val port: PersistencePort) : CliktCommand() {
  val gameId: Int by argument("Game ID").int()

  override fun run() {
    val initialFEN = FEN()
    val initialGame = Game(gameId, initialFEN)
    port.saveGame(initialGame, false)
    echo("New game with id $gameId created.")
  }
}
