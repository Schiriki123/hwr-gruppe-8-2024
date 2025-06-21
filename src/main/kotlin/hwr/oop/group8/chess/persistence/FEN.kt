package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.piece.PieceType

data class FEN(
  private val boardString: String =
    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
  private val turn: Char = 'w',
  val castle: String = "KQkq",
  val enPassant: String = "-",
  val halfmoveClock: Int = 0,
  val fullmoveClock: Int = 1,
) {
  init {
    // boardString length 8 ranks + 7 slashes + 1 king
    require(boardString.length >= 8 + 7 + 1) {
      "Board string must be 16 or higher"
    }
    require(turn == 'w' || turn == 'b') { "Turn must be either 'w' or 'b'" }
    require(
      castle.all {
        it in "KQkq"
      }.or(castle == "-"),
    ) {
      "Castle string can only contain 'K', 'Q', 'k', 'q' or '-'"
    }
    require(halfmoveClock >= 0) { "Halfmove clock must be non-negative." }
    require(fullmoveClock > 0) { "Fullmove clock must be positive." }
  }

  fun enPassant(): Position? = if (enPassant == "-") {
    null
  } else {
    Position.fromString(enPassant)
  }

  fun getRank(rank: Rank): String =
    boardString.split("/").reversed()[rank.toInt() - 1]

  fun getTurn(): Color = if (turn == 'w') Color.WHITE else Color.BLACK

  fun hashOfBoard() = boardString.hashCode()

  companion object {
    fun convertChar(pieceChar: Char): Pair<PieceType, Color> =
      when (pieceChar) {
        'r' -> Pair(PieceType.ROOK, Color.BLACK)
        'k' -> Pair(PieceType.KING, Color.BLACK)
        'q' -> Pair(PieceType.QUEEN, Color.BLACK)
        'b' -> Pair(PieceType.BISHOP, Color.BLACK)
        'n' -> Pair(PieceType.KNIGHT, Color.BLACK)
        'p' -> Pair(PieceType.PAWN, Color.BLACK)
        'R' -> Pair(PieceType.ROOK, Color.WHITE)
        'N' -> Pair(PieceType.KNIGHT, Color.WHITE)
        'B' -> Pair(PieceType.BISHOP, Color.WHITE)
        'K' -> Pair(PieceType.KING, Color.WHITE)
        'Q' -> Pair(PieceType.QUEEN, Color.WHITE)
        'P' -> Pair(PieceType.PAWN, Color.WHITE)
        else -> throw IllegalArgumentException(
          "Invalid piece character: $pieceChar",
        )
      }

    fun generateFENBoardString(board: Board): String {
      val builder = StringBuilder()
      var lastPiece = 0
      for (rank in Rank.entries.reversed()) {
        for (file in File.entries) {
          val piece = board.analyser.getPieceAt(Position(file, rank))
          if (piece != null) {
            if (lastPiece != 0) {
              builder.append(lastPiece)
            }
            builder.append(piece.fenRepresentation())
            lastPiece = 0
          } else {
            lastPiece++
          }
        }
        if (lastPiece != 0) builder.append(lastPiece)
        lastPiece = 0
        builder.append('/')
      }
      return builder.toString().dropLast(1)
    }

    fun boardStateHash(board: Board): Int =
      generateFENBoardString(board).hashCode()

    fun getFEN(board: Board): FEN = FEN(
      generateFENBoardString(board),
      if (board.turn == Color.WHITE) 'w' else 'b',
      board.castle,
      board.enPassant?.toString() ?: "-",
      board.halfmoveClock,
      board.fullmoveClock,
    )
  }

  override fun toString(): String =
    "$boardString $turn $castle $enPassant $halfmoveClock $fullmoveClock"
}
