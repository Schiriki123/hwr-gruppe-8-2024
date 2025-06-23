package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.Position

class EnPassantMove(val from: Position, val to: Position) : Move {
  override fun moves(): List<SingleMove> = listOf(SingleMove(from, to))
  override fun specialCapture(): Position =
    if (from.rank.distanceTo(to.rank) == -1) {
      Position(to.file, to.rank.down())
    } else {
      Position(to.file, to.rank.up())
    }
}
