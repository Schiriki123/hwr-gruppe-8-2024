package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move

class Pawn(override val color: Color) : Piece {
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


    if (move.isMoveStraight()) {
      val nextField = from.getAdjacentPosition(forwardDirection)
      if (nextField != to) {
        if (from.rank == startRank) {
          val nextNextField = nextField.getAdjacentPosition(forwardDirection)
          if (board.getSquare(nextField).getPiece() != null && board.getSquare(
              nextNextField
            ).getPiece() != null
          ) {
            return false
          }
        } else {
          return false
        }
      }
      if (board.getSquare(nextField).getPiece() != null) {
        return false
      }
    } else if (move.isMoveDiagonal()) {
      val nextField = from.getAdjacentPosition(direction)
      if (nextField != to) {
        return false
      }
      if (board.getSquare(nextField).getPiece() == null) {
        return false
      }

    } else return false

    return true
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'P'
      Color.BLACK -> 'p'
    }
  }
}
