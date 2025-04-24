package hwr.oop.chess

import com.sun.org.apache.xpath.internal.operations.Bool

class Board(val board: HashMap<Position, Square> = HashMap<Position, Square>()) {
  init {
    for (i  in 'a'..'h') {
      for (j  in 1..8) {
        val tempPosition= Position(i,j)
        val tempPiece = Pawn(true)
        val tempSquare= Square(i,j,tempPiece)

        board[tempPosition] = tempSquare
      }
    }
  }

   private fun validateMove(move: Move, isWhite:Boolean):Boolean {
      if(this.board[move.start]?.piece?.isWhite ==isWhite && this.board[move.end]?.piece?.isWhite != isWhite) {
        val possibleMoves= this.board[move.start]?.piece?.move(move.start)
        if(possibleMoves != null && possibleMoves.contains(move.end)) {
          return true
        }
      }
      return false
    }

  fun makeMove(move: Move, isWhite: Boolean): Boolean {

    return validateMove(move,isWhite);
  }

}



