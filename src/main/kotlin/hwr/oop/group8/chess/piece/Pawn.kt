package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position

class Pawn(
  override val color: Color,
  val boardInspector: BoardInspector,
) : Piece {

  fun getPosition(): Position {
    return boardInspector.findPositionOfPiece(this)
  }

  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val forwardDirection: Direction
    val startRank: Int

    if (color == Color.WHITE) {
      forwardDirection = Direction.TOP
      startRank = 2
    } else {
      forwardDirection = Direction.BOTTOM
      startRank = 7
    }

    // Check for straight move
    val nextField =
      getPosition().nextPosition(forwardDirection)
    if (boardInspector.getPieceAt(nextField) == null) {
      validDestinations.add(nextField)
      // Check for double move from starting position
      if (getPosition().rank == startRank) {
        val twoSquaresForward = nextField.nextPosition(forwardDirection)
        if (boardInspector.getPieceAt(twoSquaresForward) == null) {
          validDestinations.add(twoSquaresForward)
        }
      }
    }

    // Check for diagonal captures
    for (direction in setOf(
      Direction.LEFT.combine(forwardDirection),
      Direction.RIGHT.combine(forwardDirection)
    )) {
      if (getPosition().hasNextPosition(direction)) {
        val nextPosition = getPosition().nextPosition(direction)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece != null && nextPiece.color != color) {
          validDestinations.add(nextPosition)
        }
      }
    }

    return validDestinations.toSet()
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'P'
      Color.BLACK -> 'p'
    }
  }
}
