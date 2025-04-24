package hwr.oop.chess

class Move(val start: Position, val end: Position) {
  fun isMoveValid(board: Board): Boolean {
    val piece: Piece = TODO("Implement getSquare")
    val possibleMoves: Set<Position> = piece.move(start)
    val targetSquare: Square = TODO("Implement getSquare")

    // Check if the target position is a valid move for the piece
    if (!possibleMoves.contains(end)) {
      return false
    }

    // Check if goal is already occupied by a piece of the same color
    if (targetSquare.piece.isWhite == piece.isWhite) {
      return false
    }


    return true
  }
}
