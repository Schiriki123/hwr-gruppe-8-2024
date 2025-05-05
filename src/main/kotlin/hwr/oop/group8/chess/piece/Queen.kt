package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

class Queen(override val color: Color) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()

    if (move.isMoveStraight()) {
      check(move.isMoveStraight()) { "Invalid move for piece Queen from $from to $to" }
      from = from.getAdjacentPosition(direction) // Skip current square
      while (from != to) {
        if (board.getSquare(from).getPiece() != null) {
          return false
        }
        from = from.getAdjacentPosition(direction)
      }
    }
    else {
      check(move.isMoveDiagonal()) { "Invalid move for piece Queen from $from to $to" }
      from = from.getAdjacentPosition(direction) // Skip current square
      while (from != to) {
        if (board.getSquare(from).getPiece() != null) {
          return false
        }
        from = from.getAdjacentPosition(direction)
      }
    }
    return true
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'Q'
      Color.BLACK -> 'q'
    }
  }
}
