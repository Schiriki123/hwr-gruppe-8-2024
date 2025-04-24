package hwr.oop.chess

class Board(val board: HashMap<Position, Square> = HashMap<Position, Square>()) {
  init {
    for (i in 'a'..'h') {
      for (j in 1..8) {
        val tempPosition = Position(i, j)
        val tempSquare = Square(null)
        board[tempPosition] = tempSquare
      }
    }
  }

  private fun validateMove(move: Move, currentPlayer: Boolean): Boolean {
    val startSquare = this.board[move.start]
    val endSquare = this.board[move.end]
    if (endSquare?.piece?.isWhite != currentPlayer) {
      return false
    }
    if (startSquare?.piece?.isWhite == currentPlayer) {

    }
    return false
  }

  fun makeMove(move: Move, isWhite: Boolean): Boolean {

    return validateMove(move, isWhite);
  }

}



