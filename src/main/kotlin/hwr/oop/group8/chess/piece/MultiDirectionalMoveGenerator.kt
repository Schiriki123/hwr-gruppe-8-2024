package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move

class MultiDirectionalMoveGenerator(
  val piece: Piece,
  val boardInspector: BoardInspector,
  val directions: Set<Direction>,
) {
  fun getValidMoveDestinations(): Set<Move> {
    val validDestinations: MutableSet<Move> = mutableSetOf()
    val currentPosition = boardInspector.findPositionOfPiece(piece)
    for (dir in directions) {
      var nextPosition = currentPosition
      while (nextPosition.hasNextPosition(dir)) {
        nextPosition = nextPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece == null) {
          validDestinations.add(Move(currentPosition, nextPosition))
        } else if (nextPiece.color != piece.color) {
          validDestinations.add(Move(currentPosition, nextPosition))
          break
        } else {
          break
        }
      }
    }
    return validDestinations
  }
}
