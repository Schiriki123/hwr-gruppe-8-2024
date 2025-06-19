package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.piece.PieceType

class EnPassantAnalyser(val inspector: BoardInspectorEnPassant) {
  fun setAllowedEnPassantMoves(move: DoublePawnMove) {
    val currentTurn = inspector.getCurrentTurn()
    if (move.to.hasNextPosition(Direction.LEFT) &&
      inspector.getPieceAt(move.to.left())?.color != currentTurn &&
      inspector.getPieceAt(move.to.left())?.getType() == PieceType.PAWN
    ) {
      inspector.setEnPassant(move.skippedPosition())
    } else if (move.to.hasNextPosition(Direction.RIGHT) &&
      inspector.getPieceAt(move.to.right())?.color != currentTurn &&
      inspector.getPieceAt(move.to.right())?.getType() == PieceType.PAWN
    ) {
      inspector.setEnPassant(move.skippedPosition())
    }
  }
}
