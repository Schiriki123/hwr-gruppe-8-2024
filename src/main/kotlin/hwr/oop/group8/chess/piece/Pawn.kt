package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move

class Pawn(override val color: Color,
           override val boardInspector: BoardInspector
) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    val from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    val forwardDirection: Direction
    val startRank: Int

    if (color == Color.WHITE) {
      forwardDirection = Direction.TOP
      startRank = 2
    } else {
      forwardDirection = Direction.BOTTOM
      startRank = 7
    }

    // Handle straight moves
    if (move.isMoveStraight()) {
      val nextField = forwardDirection.nextPosition(from)

      // Check if target square is occupied
      if (board.getSquare(to).getPiece() != null) {
        return false
      }

      // Single square forward move
      if (nextField == to) {
        return true
      }

      // Double square forward move from start position
      if (from.rank == startRank) {
        val twoSquaresForward = forwardDirection.nextPosition(nextField)
        if (twoSquaresForward == to) {
          // Path must be clear for double move
          return board.getSquare(nextField).getPiece() == null
        }
      }

      return false
    }
    // Handle diagonal captures
    else {
      // Must be exactly one square diagonally
      if (to != direction.nextPosition(from)) {
        return false
      }

      // Must capture piece
      val targetPiece = board.getSquare(to).getPiece()
      return targetPiece != null
    }
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'P'
      Color.BLACK -> 'p'
    }
  }
}
