package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position

class Queen(
  override val color: Color,
  val boardInspector: BoardInspector,
) : Piece {
  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = Direction.entries

    for (dir in directions) {
      var nextPosition = boardInspector.findPositionOfPiece(this)
      while (nextPosition.hasNextPosition(dir)) {
        nextPosition = nextPosition.nextPosition(dir)
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
      Color.WHITE -> 'Q'
      Color.BLACK -> 'q'
    }
  }
}
