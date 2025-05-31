package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.PieceType

interface Move {
  fun moves(): List<SingleMove>

  companion object {
    fun create(from: Position, to: Position): Move = SingleMove(from, to)
  }

  fun promotesTo(): PieceType? = null
  fun isPromotion(): Boolean = false
}
