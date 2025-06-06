package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.piece.PieceType

data class PromotionMove(
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
