package hwr.oop.chess

data class Rook(
  override val isWhite: Boolean,
  override val isCaptured: Boolean = false
): Piece {
    override fun move(position: Position): Set<Position> {
      val possibleMoves = mutableSetOf<Position>()
      val currentRank = position.rank
      val currentFile = position.file
      // Add all possible moves to set
      for (rank in 1..8) {
          if (rank != currentRank) {
              possibleMoves.add(Position(currentFile, rank))
          }
      }
      for (file in 'a'..'h') {
          if (file != currentFile) {
              possibleMoves.add(Position(file, currentRank))
          }
      }
      return possibleMoves.toSet()
    }
}
