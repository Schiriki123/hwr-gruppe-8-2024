package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position

class King(
  override val color: Color,
  override val boardInspector: BoardInspector,
) : Piece {
  @Deprecated("Use BoardInspector instead to reference the board")
  override fun isMoveValid(move: Move, board: Board): Boolean {

    val from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    val nextField = from.getAdjacentPosition(direction)

    // Single square move
    if (to != nextField) {
      return false
    }
    return true
  }

  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = Direction.entries // All possible directions

    val myPosition = myPosition()
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

    return validDestinations.toSet()
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'K'
      Color.BLACK -> 'k'
    }
  }
}
