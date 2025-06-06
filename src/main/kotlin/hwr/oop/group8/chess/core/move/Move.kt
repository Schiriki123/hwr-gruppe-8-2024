package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.piece.PieceType

interface Move {
  fun moves(): List<SingleMove> // TODO: Maybe to Move

  fun promotesTo(): PieceType? = null
  fun isPromotion(): Boolean = false
  fun isDoublePawnMove(): Boolean = false
}
