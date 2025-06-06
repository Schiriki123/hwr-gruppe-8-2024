package hwr.oop.group8.chess.core.piece

enum class PieceType(val allowedPromotion: Boolean = false) {
  PAWN,
  ROOK(allowedPromotion = true),
  KNIGHT(allowedPromotion = true),
  BISHOP(allowedPromotion = true),
  QUEEN(allowedPromotion = true),
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
