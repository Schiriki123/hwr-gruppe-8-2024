package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.PieceType

class PromotionMove(
  val from: Position,
  val to: Position,
  val promotesTo: PieceType,
) : Move {
  init {
    require(promotesTo.allowedPromotion) {
      "Invalid promotion piece type: $promotesTo"
    }
  }

  override fun moves(): List<SingleMove> = listOf(SingleMove(from, to))

  override fun promotesTo(): PieceType = promotesTo
  override fun isPromotion(): Boolean = true
}
