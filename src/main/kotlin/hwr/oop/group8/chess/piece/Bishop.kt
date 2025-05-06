package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

class Bishop(override val color: Color,
             override val boardInspector: BoardInspector
) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    check(move.isMoveDiagonal()) { "Invalid move for piece Bishop from $from to $to" }
    from = direction.nextPosition(from) // Skip start square
    while (from != to) {
      if (board.getSquare(from).getPiece() != null) {
        return false
      }
      from = direction.nextPosition(from)
    }
    return true
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'B'
      Color.BLACK -> 'b'
    }
  }
}
