package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position

class Rook(override val color: Color,
           override val boardInspector: BoardInspector
) : Piece {
  @Deprecated("Use BoardInspector instead to reference the board")
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    check(move.isMoveStraight()) { "Invalid move for piece Rook from $from to $to" }
    from = direction.nextPosition(from) // Skip current
    while (from != to) {
      if (board.getSquare(from).getPiece() != null) {
        return false
      }
      from = direction.nextPosition(from)
    }
    return true
  }

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
