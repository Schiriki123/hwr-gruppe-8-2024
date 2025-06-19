package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.PromotionMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.core.piece.PieceType

class CliMove(val from: Position, val to: Position, val promotesTo: Char?) {
  fun toDomainMove(): Move = if (promotesTo != null) {
    PromotionMove(from, to, PieceType.fromChar(promotesTo))
  } else {
    SingleMove(from, to)
  }
}
