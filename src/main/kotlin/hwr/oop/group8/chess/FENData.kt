package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.*

data class FENData(
  private val boardString: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
  private val turn: Char = 'w',
  val castle: String = "KQkq",
  val halfmoveClock: Int = 0,
  val fullmoveClock: Int = 1,
) {
  init {
    // boardString length 8 ranks + 7 slashes
    require(boardString.length >= 8 + 7) { "Board string must be 15 or higher" }
    require(turn == 'w' || turn == 'b') { "Turn must be either 'w' or 'b'" }
    require(castle.all { it in "KQkq" }) { "Castle string can only contain 'K', 'Q', 'k', or 'q'" }
    require(halfmoveClock >= 0) { "Halfmove clock must be non-negative." }
    require(fullmoveClock > 0) { "Fullmove clock must be positive." }
  }

  fun getRank(rank: Int): String {
    require(rank in 1..8) { "Rank must be between 1 and 8" }
    return boardString.split("/")[rank - 1]
  }

  fun getTurn(): Color {
    return if (turn == 'w') Color.WHITE else Color.BLACK
  }

  companion object {
    fun createPieceOnBoard(pieceChar: Char, board: Board): Piece {
      val boardInspector = BoardInspector {
        board.getSquare(it).getPiece()
      }
      return when (pieceChar) {
        'r' -> Rook(Color.BLACK, boardInspector)
        'n' -> Knight(Color.BLACK, boardInspector)
        'b' -> Bishop(Color.BLACK, boardInspector)
        'q' -> Queen(Color.BLACK, boardInspector)
        'k' -> King(Color.BLACK, boardInspector)
        'p' -> Pawn(Color.BLACK, boardInspector)
        'R' -> Rook(Color.WHITE, boardInspector)
        'N' -> Knight(Color.WHITE, boardInspector)
        'B' -> Bishop(Color.WHITE, boardInspector)
        'Q' -> Queen(Color.WHITE, boardInspector)
        'K' -> King(Color.WHITE, boardInspector)
        'P' -> Pawn(Color.WHITE, boardInspector)
        else -> throw IllegalArgumentException("Invalid piece character: $pieceChar")
      }
    }
  }
}

