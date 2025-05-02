package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

class Bishop(override val color: Color) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    val from = move.from
    val to = move.to
    if (from != to) {
      val direction = move.getMoveDirection()

      // Check that the move is diagonal
      check(move.isMoveDiagonal()) { "Invalid move for piece Bishop from $from to $to" }

      val nextField = from.getAdjacentPosition(direction)
      if (board.getSquare(nextField).getPiece() != null) {
        return false
      }
      return isMoveValid(
        Move(nextField, to), board
      )
    }
    return true
  }

  override fun getChar(): Char {
    return when(color){
      Color.WHITE -> 'B'
      Color.BLACK -> 'b'
    }
  }
}
