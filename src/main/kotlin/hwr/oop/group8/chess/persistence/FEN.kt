package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Pawn
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook

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

  fun getRank(rank: Rank): String =
    boardString.split("/").reversed()[rank.toInt() - 1]

  fun getTurn(): Color = if (turn == 'w') Color.WHITE else Color.BLACK

  fun hashOfBoard() = boardString.hashCode()

  companion object {
    fun createPieceOnBoard(pieceChar: Char, board: BoardInspector): Piece =
      when (pieceChar) {
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
        else -> throw IllegalArgumentException(
          "Invalid piece character: $pieceChar",
        )
      }

    fun generateFENBoardString(board: Board): String {
      val builder = StringBuilder()
      var lastPiece = 0
      for (rank in Rank.entries.reversed()) {
        for (file in File.entries) {
          val piece = board.getPieceAt(Position(file, rank))
          if (piece != null) {
            if (lastPiece != 0) {
              builder.append(lastPiece)
            }
            builder.append(piece.getChar())
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

    fun getFENData(board: Board): FEN = FEN(
      generateFENBoardString(board),
      if (board.turn == Color.WHITE) 'w' else 'b',
      board.castle,
      board.enPassant,
      board.halfmoveClock,
      board.fullmoveClock,
    )
  }

  override fun toString(): String =
    "$boardString $turn $castle $enPassant $halfmoveClock $fullmoveClock"
}
