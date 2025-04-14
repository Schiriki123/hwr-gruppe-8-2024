package hwr.oop.chess

data class Pawn(
  override val isWhite: Boolean,
  override val isCaptured: Boolean = false,
) : Piece {
  override fun move(position: Position): Set<Position> {
    val possibleMoves = mutableSetOf<Position>()
    val currentRank = position.rank
    val currentFile = position.file
    // Add all possible moves to set
    if (isWhite) {
      // White pawn moves up
      possibleMoves.add(Position(currentFile, currentRank + 1))
      if (currentRank == 2) {
        possibleMoves.add(Position(currentFile, currentRank + 2))
      }
      possibleMoves.add(Position(currentFile + 1, currentRank + 1)) // Capture diagonally to the right
      possibleMoves.add(Position(currentFile - 1, currentRank + 1)) // Capture diagonally to the left
    } else {
      TODO("Implement black pawn movement")
    }
    return possibleMoves
  }
}
