package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move

class Rook(
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
      Direction.BOTTOM,
      Direction.TOP,
      Direction.LEFT,
      Direction.RIGHT
    )
    val rookMovement =
      MultiDirectionalPiece(color, boardInspector, directions, currentPosition)
    val validDestinations = rookMovement.getValidMoveDestinations().toSet()

    return validDestinations.toSet()
  }

  override fun saveMoveToHistory(move: Move) {
    moveHistory.add(move)
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'R'
      Color.BLACK -> 'r'
    }
  }
}
