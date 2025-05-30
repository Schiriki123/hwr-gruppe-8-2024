package hwr.oop.group8.chess.piece

enum class PieceType {
  PAWN,
  ROOK,
  KNIGHT,
  BISHOP,
  QUEEN,
  KING,
  ;

  companion object {
    fun fromChar(char: Char): PieceType = when (char.lowercaseChar()) {
      'p' -> PAWN
      'r' -> ROOK
      'n' -> KNIGHT
      'b' -> BISHOP
      'q' -> QUEEN
      'k' -> KING
      else -> throw IllegalArgumentException("Invalid piece character: $char")
    }
  }
}
