package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move

class Queen(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val directions = Direction.entries.toSet()
    val queenMovement =
      MultiDirectionalMoveGenerator(this, boardInspector, directions)
    val validDestinations = queenMovement.getValidMoveDestinations().toSet()

    return validDestinations.toSet()
  }

  override fun moveCallback(move: Move) {}

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'Q'
    Color.BLACK -> 'q'
  }
}
