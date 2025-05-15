package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.piece.Bishop
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Knight
import hwr.oop.group8.chess.piece.Pawn
import hwr.oop.group8.chess.piece.Piece
import hwr.oop.group8.chess.piece.Queen
import hwr.oop.group8.chess.piece.Rook

data class FENData(
  private val boardString: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
  private val turn: Char = 'w',
  val castle: String = "KQkq",
  val enPassant: String = "-",
  val halfmoveClock: Int = 0,
  val fullmoveClock: Int = 1,
) {
  init {
    // boardString length 8 ranks + 7 slashes + 1 king
    require(boardString.length >= 8 + 7 + 1) { "Board string must be 16 or higher" }
    require(turn == 'w' || turn == 'b') { "Turn must be either 'w' or 'b'" }
    require(castle.all { it in "KQkq" }) { "Castle string can only contain 'K', 'Q', 'k', or 'q'" }
    require(halfmoveClock >= 0) { "Halfmove clock must be non-negative." }
    require(fullmoveClock > 0) { "Fullmove clock must be positive." }
  }

  fun getRank(rank: Int): String {
    require(rank in 1..8) { "Rank must be between 1 and 8" }
    return boardString.split("/")[8 - rank]
  }

  fun getTurn(): Color {
    return if (turn == 'w') Color.WHITE else Color.BLACK
  }

  companion object {
    fun createPieceOnBoard(pieceChar: Char, board: BoardInspector): Piece {
      return when (pieceChar) {
        'r' -> Rook(Color.BLACK, board)
        'n' -> Knight(Color.BLACK, board)
        'b' -> Bishop(Color.BLACK, board)
        'q' -> Queen(Color.BLACK, board)
        'k' -> King(Color.BLACK, board)
        'p' -> Pawn(Color.BLACK, board)
        'R' -> Rook(Color.WHITE, board)
        'N' -> Knight(Color.WHITE, board)
        'B' -> Bishop(Color.WHITE, board)
        'Q' -> Queen(Color.WHITE, board)
        'K' -> King(Color.WHITE, board)
        'P' -> Pawn(Color.WHITE, board)
        else -> throw IllegalArgumentException("Invalid piece character: $pieceChar")
      }
    }
  }

  override fun toString(): String {
    return "$boardString $turn $castle $enPassant $halfmoveClock $fullmoveClock"
  }
}
