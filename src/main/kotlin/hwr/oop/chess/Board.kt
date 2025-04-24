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

  private fun validateMove(move: Move) {
    val movedPiece: Piece? = getSquare(move.start).piece
    val targetSquare = getSquare(move.end)

    // Validate that start square contains piece
    if (movedPiece == null) throw IllegalArgumentException("Start square does not contain any piece")

    // Check if target is already occupied by a piece of the same color
    if (targetSquare.piece?.isWhite == movedPiece.isWhite) throw IllegalArgumentException("Target square is occupied by ally piece")

    // Check if the target position is a valid move for the piece
    val possibleMoves = movedPiece.move(move.start)
    if (!possibleMoves.contains(move.end)) {
      throw IllegalArgumentException("Impossible move")
    }
  }

  fun makeMove(move: Move): Boolean {/* TODO Validate movement
    - Player
    - Same color on end square
    - Is end position contained in move set
    - Is path blocked
    - Check for mate
    */
    validateMove(move)

    val startSquare = getSquare(move.start)
    val endSquare = getSquare(move.end)

    val movedPiece = startSquare.piece
    endSquare.piece = movedPiece
    startSquare.piece = null
    isItWhitesMove = !isItWhitesMove

    return true
  }

}



