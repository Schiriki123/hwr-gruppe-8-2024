package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.persistence.PersistencePort

class ShowGameCommand(private val persistencePort: PersistencePort) :
  CliCommand {
  override fun matches(args: List<String>): Boolean {
    if (args.size != 3) return false
    val firstTwoArgsMatch = args.subList(0, 2) == listOf("show", "game")
    val thirdArgIsNumber = args[2].toIntOrNull() != null

    return firstTwoArgsMatch && thirdArgIsNumber
  }

  override fun handle(args: List<String>) {
    val gameId = args[2].toInt()
    val game = persistencePort.loadGame(gameId)
    println("Loading game with id $gameId...")
    println("Current board:")
    printBoard(game.board.analyser)
    println("Current turn: ${game.getFen().getTurn()}")
    printCapturedPieces(game.board.analyser)
  }

  private fun printBoard(board: BoardInspector) {
    val builder = StringBuilder()
    for (rank in Rank.entries.reversed()) {
      for (file in File.entries) {
        val piece = board.getPieceAt(Position(file, rank))
        builder.append(piece?.fenRepresentation() ?: '.')
      }
      builder.append("${System.lineSeparator()}")
    }
    println(builder.toString().trim())
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
        val piece = boardInspector.getPieceAt(position)
        piece?.let { piece ->
          if (piece.color == Color.WHITE) {
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
    println(
      """
        White's captures: $blackPieces
        Black's captures: $whitePieces
      """.trimIndent(),
    )
  }
}
