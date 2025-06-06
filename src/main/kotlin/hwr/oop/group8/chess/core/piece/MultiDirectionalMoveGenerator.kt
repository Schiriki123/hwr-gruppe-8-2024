package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.SingleMove

class MultiDirectionalMoveGenerator(
  val piece: Piece,
  val boardInspector: BoardInspector,
  val directions: Set<Direction>,
) { // TODO: Improve readability
  fun getValidMoveDestinations(): Set<SingleMove> {
    val validDestinations: MutableSet<SingleMove> = mutableSetOf()
    val currentPosition = boardInspector.findPositionOfPiece(piece)
    for (dir in directions) {
      var nextPosition = currentPosition
      while (nextPosition.hasNextPosition(dir)) {
        nextPosition = nextPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece == null) {
          validDestinations.add(SingleMove(currentPosition, nextPosition))
        } else if (nextPiece.color != piece.color) {
          validDestinations.add(SingleMove(currentPosition, nextPosition))
          break
        } else {
          break
        }
      }
    }
    return validDestinations
  }
}
