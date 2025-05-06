package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position

class Queen(override val color: Color,
            override val boardInspector: BoardInspector
) : Piece {
  @Deprecated("Use BoardInspector instead to reference the board")
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()

    if (move.isMoveStraight()) {
      check(move.isMoveStraight()) { "Invalid move for piece Queen from $from to $to" }
      from = from.getAdjacentPosition(direction)
    }
    else {
      check(move.isMoveDiagonal()) { "Invalid move for piece Queen from $from to $to" }
      from = from.getAdjacentPosition(direction)
    }
    while (from != to) {
      if (board.getSquare(from).getPiece() != null) {
        return false
      }
      from = from.getAdjacentPosition(direction)
    }
    return true
  }

  override fun getValidMoveDestinations(): Set<Position> {
    val validDestinations: MutableSet<Position> = mutableSetOf()
    val directions = Direction.entries

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
      Color.WHITE -> 'Q'
      Color.BLACK -> 'q'
    }
  }
}
