package hwr.oop.chess

data class Rook(
  override val isWhite: Boolean,
  override val isCaptured: Boolean = false
): Piece {
    override fun move(startPosition: Position): Set<Position> {
      val possibleMoves = mutableSetOf<Position>()
      // Add all possible moves to set
      for (rank in 1..8) {
        if (rank != startPosition.rank) { // Exclude the current position
          possibleMoves.add(Position(startPosition.file, rank))
        }
      }
      for (file in 'a'..'h') {
        if (file != startPosition.file) { // Exclude the current position
          possibleMoves.add(Position(file, startPosition.rank))
        }
      }
      return possibleMoves
    }
}
