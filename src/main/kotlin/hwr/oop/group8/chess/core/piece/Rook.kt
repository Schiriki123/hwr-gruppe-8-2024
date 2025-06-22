package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.move.SingleMove

class Rook(val color: Color, val boardInspector: BoardInspector) : Piece {
  override fun color(): Color = color
  override fun validMoves(): Set<SingleMove> {
    val directions = setOf(
      Direction.BOTTOM,
      Direction.TOP,
      Direction.LEFT,
      Direction.RIGHT,
    )
    val rookMovement =
      MultiDirectionalMoveGenerator(this, boardInspector, directions)
    val validDestinations = rookMovement.validMoves().toSet()

    return validDestinations.toSet()
  }

  override fun fenRepresentation(): Char = when (color) {
    Color.WHITE -> 'R'
    Color.BLACK -> 'r'
  }

  override fun pieceType(): PieceType = PieceType.ROOK
}
