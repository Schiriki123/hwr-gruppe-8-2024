package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import hwr.oop.group8.chess.persistence.PersistencePort

class DeleteGame(private val port: PersistencePort) : CliktCommand() {
  val gameId by argument("Game ID").int()

  override fun run() {
    port.deleteGame(gameId)
    echo("Game with ID $gameId deleted.")
  }
}
