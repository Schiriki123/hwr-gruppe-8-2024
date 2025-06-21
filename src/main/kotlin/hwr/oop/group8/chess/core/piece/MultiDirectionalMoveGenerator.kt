package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.move.SingleMove

class MultiDirectionalMoveGenerator(
  private val piece: Piece,
  private val boardInspector: BoardInspector,
  private val directions: Set<Direction>,
) {
  fun getValidMoveDestinations(): Set<SingleMove> = directions.flatMap { dir ->
    collectMovesInDirection(dir)
  }.toSet()

  private fun collectMovesInDirection(dir: Direction): Set<SingleMove> {
    val moves = mutableSetOf<SingleMove>()
    var pos = boardInspector.findPositionOfPiece(piece)
    while (pos.hasNextPosition(dir)) {
      pos = pos.nextPosition(dir)
      if (reachedPiece(moves, pos)) break
    }
    return moves
  }

  private fun reachedPiece(
    moves: MutableSet<SingleMove>,
    pos: Position,
  ): Boolean {
    val currentPosition = boardInspector.findPositionOfPiece(piece)
    val nextPiece = boardInspector.getPieceAt(pos)
    return if (nextPiece == null) {
      moves.add(SingleMove(currentPosition, pos))
      false
    } else if (nextPiece.color() != piece.color()) {
      moves.add(SingleMove(currentPosition, pos))
      true
    } else {
      true
    }
  }
}
