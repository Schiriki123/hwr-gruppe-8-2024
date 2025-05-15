package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position

class Bishop(
  override val color: Color,
  val boardInspector: BoardInspector,
) : Piece {
  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = setOf(
      Direction.BOTTOM_RIGHT,
      Direction.BOTTOM_LEFT,
      Direction.TOP_LEFT,
      Direction.TOP_RIGHT
    )

    for (dir in directions) {
      var nextPosition = boardInspector.findPositionOfPiece(this)
      while (dir.hasNextPosition(nextPosition)) {
        nextPosition = dir.nextPosition(nextPosition)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece == null) {
          validDestinations.add(nextPosition)
        } else if (nextPiece.color != color) {
          validDestinations.add(nextPosition)
          break
        } else {
          break
        }
      }
    }

    return validDestinations.toSet()
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'B'
      Color.BLACK -> 'b'
    }
  }
}
