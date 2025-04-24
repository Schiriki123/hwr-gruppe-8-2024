package hwr.oop.chess

class Board(
  val boardMap: HashMap<Position, Square> = HashMap<Position, Square>(),
  var isItWhitesMove: Boolean = true,
) {
  init {
    for (i in 'a'..'h') {
      for (j in 1..8) {
        val tempPosition = Position(i, j)
        val tempSquare = Square(null)
        boardMap[tempPosition] = tempSquare
      }
    }
  }

  fun putPiece(position: Position, piece: Piece) {
    val square = boardMap.getValue(position)
    square.piece = piece
  }

  fun getSquare(position: Position): Square {
    return boardMap.getValue(position)
  }

  private fun validateMove(move: Move): Boolean {
    val startSquare = this.boardMap[move.start]
    val endSquare = this.boardMap[move.end]
    if (endSquare?.piece?.isWhite != isItWhitesMove) {
      return false
    }
    if (startSquare?.piece?.isWhite == isItWhitesMove) {

    }
    return false
  }

  fun makeMove(move: Move): Boolean {/* TODO Validate movement
    - Player
    - Same color on end square
    - Is end position contained in move set
    - Is path blocked
    - Check for mate
    */
    val startSquare = getSquare(move.start)
    val endSquare = getSquare(move.end)

    if (startSquare.piece == null) return false

    val movedPiece = startSquare.piece
    endSquare.piece = movedPiece
    startSquare.piece = null
    isItWhitesMove = !isItWhitesMove

    return true
  }

}



