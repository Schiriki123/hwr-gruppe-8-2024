package hwr.oop.group8.chess.piece

enum class PieceType(val allowedPromotion: Boolean = false) {
  PAWN,
  ROOK(true),
  KNIGHT(true),
  BISHOP(true),
  QUEEN(true),
  KING,
}
