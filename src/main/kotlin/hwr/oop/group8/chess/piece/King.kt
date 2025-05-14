package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Position

class King(
  override val color: Color,
  val boardInspector: BoardInspector,
  var hasMoved: Boolean = false,
) : Piece {

  override fun hasMoved(): Boolean {
    return hasMoved
  }

  override fun moved() {
    hasMoved = true
  }

  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = Direction.entries // All possible directions

    val myPosition = boardInspector.findPositionOfPiece(this)
    for (dir in directions) {
      if (dir.hasNextPosition(myPosition)) {
        val nextPosition = dir.nextPosition(myPosition)
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
          validDestinations.add(Position('b', 1))
        }
        if (color == Color.BLACK) {
          validDestinations.add(Position('b', 8))
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
