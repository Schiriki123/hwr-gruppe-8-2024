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
      Position.createPositionIfValid(startPosition.file, startPosition.rank + 1)
        ?.let { possibleMoves.add(it) }
      if (startPosition.rank == 2) {
        Position.createPositionIfValid(
          startPosition.file, startPosition.rank + 2
        )?.let { possibleMoves.add(it) }
      }

      // Capture diagonally to the right
      Position.createPositionIfValid(
        startPosition.file + 1, startPosition.rank + 1
      )?.let { possibleMoves.add(it) }
      // Capture diagonally to the left
      Position.createPositionIfValid(
        startPosition.file - 1, startPosition.rank + 1
      )?.let { possibleMoves.add(it) }
    } else {
      // Black pawn moves down
      Position.createPositionIfValid(startPosition.file, startPosition.rank - 1)
        ?.let { possibleMoves.add(it) }
      if (startPosition.rank == 7) {
        Position.createPositionIfValid(
          startPosition.file, startPosition.rank - 2
        )?.let { possibleMoves.add(it) }
      }

      // Capture diagonally to the right
      Position.createPositionIfValid(
        startPosition.file + 1, startPosition.rank - 1
      )?.let { possibleMoves.add(it) }
      // Capture diagonally to the left
      Position.createPositionIfValid(
        startPosition.file - 1, startPosition.rank - 1
      )?.let { possibleMoves.add(it) }
    }
    return possibleMoves
  }
}
