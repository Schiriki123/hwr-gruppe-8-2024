package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.persistence.PersistencePort

class ShowGame(private val port: PersistencePort) : CliktCommand() {
  val gameId: Int by argument("Game ID").int()
  override fun run() {
    val game = port.loadGame(gameId)
    echo("Loading game with id $gameId...")
    echo("Current board:")
    printBoard(game.board.analyser)
    echo("Current turn: ${game.fen().getTurn()}")
    printCapturedPieces(game.board.analyser)
  }

  private fun printBoard(board: BoardInspector) {
    val builder = StringBuilder()
    for (rank in Rank.entries.reversed()) {
      for (file in File.entries) {
        val piece = board.pieceAt(Position(file, rank))
        builder.append(piece?.fenRepresentation() ?: '.')
      }
      builder.append("${System.lineSeparator()}")
    }
    echo(builder.toString().trim())
  }

  private fun printCapturedPieces(boardInspector: BoardInspector) {
    val whitePieces = StringBuilder()
    whitePieces.append("RNBQKBNR")
    whitePieces.append("PPPPPPPP")
    val blackPieces = StringBuilder()
    blackPieces.append("rnbqkbnr")
    blackPieces.append("pppppppp")
    for (rank in Rank.entries) {
      for (file in File.entries) {
        val position = Position(file, rank)
        val piece = boardInspector.pieceAt(position)
        piece?.let { piece ->
          if (piece.color() == Color.WHITE) {
            whitePieces.deleteAt(
              whitePieces.indexOf(piece.fenRepresentation()),
            )
          } else {
            blackPieces.deleteAt(
              blackPieces.indexOf(piece.fenRepresentation()),
            )
          }
        }
      }
    }
    echo(
      """
        White's captures: $blackPieces
        Black's captures: $whitePieces
      """.trimIndent(),
    )
  }
}
