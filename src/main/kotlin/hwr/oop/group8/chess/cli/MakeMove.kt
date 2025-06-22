package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.types.int
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.PersistencePort

class MakeMove(private val port: PersistencePort) : CliktCommand() {
  val gameId: Int by argument(help = "Game ID").int()
  val from: Position by argument().convert { Position.fromString(it) }
  val to: Position by argument().convert { Position.fromString(it) }
  val promotion: Char? by argument().convert { it.single() }.optional()

  override fun run() {
    val move = CliMove(from, to, promotion)
    val game = port.loadGame(gameId)
    game.makeMove(move)
    port.saveGame(game, true)

    echo("Move made from $from to $to.")
  }
}
