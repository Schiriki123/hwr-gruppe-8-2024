package hwr.oop.chess

data class Pawn(
  override val isWhite: Boolean,
  override val isCaptured: Boolean = false,
) : Piece {
  override fun move(startPosition: Position): Set<Position> {
    val possibleMoves = mutableSetOf<Position>()
    // Add all possible moves to set
    if (isWhite) {
      // White pawn moves up
      possibleMoves.add(Position(startPosition.file, startPosition.rank + 1))
      if (startPosition.rank == 2) {
        possibleMoves.add(Position(startPosition.file, startPosition.rank + 2))
      }
      possibleMoves.add(Position(startPosition.file + 1, startPosition.rank + 1)) // Capture diagonally to the right
      possibleMoves.add(Position(startPosition.file - 1, startPosition.rank + 1)) // Capture diagonally to the left
    } else {
      // Black pawn moves down
      possibleMoves.add(Position(startPosition.file, startPosition.rank - 1))
      if (startPosition.rank == 7) {
        possibleMoves.add(Position(startPosition.file, startPosition.rank - 2))
      }
      possibleMoves.add(Position(startPosition.file + 1, startPosition.rank - 1)) // Capture diagonally to the right
      possibleMoves.add(Position(startPosition.file - 1, startPosition.rank - 1)) // Capture diagonally to the left
    }
    return possibleMoves
  }
}
