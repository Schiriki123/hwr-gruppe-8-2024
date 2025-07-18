package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.move.SingleMove

class Bishop(val color: Color, val boardInspector: BoardInspector) : Piece {
  override fun color(): Color = color

  override fun validMoves(): Set<SingleMove> {
    val directions = setOf(
      Direction.BOTTOM_RIGHT,
      Direction.BOTTOM_LEFT,
      Direction.TOP_LEFT,
      Direction.TOP_RIGHT,
    )
    val bishopMovement =
      MultiDirectionalMoveGenerator(this, boardInspector, directions)
    val validDestinations = bishopMovement.validMoves().toSet()

    return validDestinations.toSet()
  }

  override fun fenRepresentation(): Char = when (color) {
    Color.WHITE -> 'B'
    Color.BLACK -> 'b'
  }

  override fun pieceType(): PieceType = PieceType.BISHOP
}
