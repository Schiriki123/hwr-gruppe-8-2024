package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.move.SingleMove

class Queen(val color: Color, val boardInspector: BoardInspector) : Piece {
  override fun color(): Color = color
  override fun validMoves(): Set<SingleMove> {
    val directions = setOf(
      Direction.BOTTOM,
      Direction.TOP,
      Direction.LEFT,
      Direction.RIGHT,
      Direction.BOTTOM_RIGHT,
      Direction.BOTTOM_LEFT,
      Direction.TOP_LEFT,
      Direction.TOP_RIGHT,
    )
    val queenMovement =
      MultiDirectionalMoveGenerator(this, boardInspector, directions)
    val validDestinations = queenMovement.validMoves().toSet()

    return validDestinations.toSet()
  }

  override fun fenRepresentation(): Char = when (color) {
    Color.WHITE -> 'Q'
    Color.BLACK -> 'q'
  }

  override fun pieceType(): PieceType = PieceType.QUEEN
}
