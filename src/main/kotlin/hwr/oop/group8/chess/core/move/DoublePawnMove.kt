package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.Position

data class DoublePawnMove(val from: Position, val to: Position) : Move {
  override fun moves(): List<SingleMove> = listOf(SingleMove(from, to))
  override fun isDoublePawnMove(): Boolean = true
  fun skippedPosition() =
    if (from.rank.distanceTo(to.rank) == 2) to.up() else to.down()
}
