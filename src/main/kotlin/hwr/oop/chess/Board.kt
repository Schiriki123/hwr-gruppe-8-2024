package hwr.oop.chess

import com.sun.org.apache.xpath.internal.operations.Bool

class Board(val board: HashMap<Position, Square> = HashMap<Position, Square>()) {
  init {
    for (i  in 'a'..'h') {
      for (j  in 1..8) {
        var tempPosition= Position(j,i)
        val tempPiece = Pawn(true)
        var tempSquare= Square(j,i,tempPiece)

        board[tempPosition] = tempSquare
      }
    }
  }

  fun validateMove(move: Move, board: Board, isWhite:Boolean):Boolean {
    val currentBoard = board.board
    if(currentBoard[move.start]?.piece?.isWhite ==isWhite) {
      if(currentBoard[move.end]?.piece?.isWhite != isWhite) {

      }
    }
    return false
  }

}

