package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Position

class Rook(override val color: Color,
           override val boardInspector: BoardInspector
) : Piece {
  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = setOf(
      Direction.BOTTOM,
      Direction.TOP,
      Direction.LEFT,
      Direction.RIGHT
    )

    for (dir in directions) {
      var nextPosition = myPosition()
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
      Color.WHITE -> 'R'
      Color.BLACK -> 'r'
    }
  }
}
