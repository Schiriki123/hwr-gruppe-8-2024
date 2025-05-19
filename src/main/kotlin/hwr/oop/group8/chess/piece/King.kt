package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position

class King(
  override val color: Color,
  val boardInspector: BoardInspector,
) : Piece {
  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = Direction.entries // All possible directions

    val myPosition = boardInspector.findPositionOfPiece(this)
    for (dir in directions) {
      if (myPosition.hasNextPosition(dir)) {
        val nextPosition = myPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)

        // Check if the next position is empty or occupied by an opponent's piece
        if (nextPiece == null || nextPiece.color != color) {
          validDestinations.add(nextPosition)
        }
      }
    }

    if (boardInspector.getCurrentTurn() == color) {
      val castling = boardInspector.isCastlingAllowed(color)
      if (castling.first) {
        if (color == Color.WHITE) {
          validDestinations.add(Position('c', 1))
        }
        if (color == Color.BLACK) {
          validDestinations.add(Position('c', 8))
        }
      }
      if (castling.second) {
        if (color == Color.WHITE) {
          validDestinations.add(Position('g', 1))
        }
        if (color == Color.BLACK) {
          validDestinations.add(Position('g', 8))
        }
      }
    }
    return validDestinations.toSet()
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'K'
      Color.BLACK -> 'k'
    }
  }
}
