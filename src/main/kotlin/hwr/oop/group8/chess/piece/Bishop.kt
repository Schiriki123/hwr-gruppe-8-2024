package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move

class Bishop(
  override val color: Color,
  val boardInspector: BoardInspector,
  override val moveHistory: MutableList<Move> = mutableListOf(),
) : Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val currentPosition = if (!moveHistory.isEmpty()) {
      moveHistory.last().to
    } else {
      boardInspector.findPositionOfPiece(this)
    }

    val directions = setOf(
      Direction.BOTTOM_RIGHT,
      Direction.BOTTOM_LEFT,
      Direction.TOP_LEFT,
      Direction.TOP_RIGHT
    )
    val bishopMovement =
      MultiDirectionalPiece(color, boardInspector, directions, currentPosition)
    val validDestinations = bishopMovement.getValidMoveDestinations().toSet()

    return validDestinations.toSet()
  }

  override fun saveMoveToHistory(move: Move) {
    moveHistory.add(move)
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'B'
      Color.BLACK -> 'b'
    }
  }
}
