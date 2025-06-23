package hwr.oop.group8.chess.core.exceptions

import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.piece.Piece

object IllegalMove {
  class CheckmateException : Exception("Game is over, checkmate!")
  class DrawException(message: String) : Exception(message)
  class NoPieceException(move: Move) :
    Exception("There is no piece at ${move.moves().first().from}")

  class OutOfTurnException : Exception("It's not your turn")
  class InvalidMoveForPieceException(piece: Piece, move: Move) :
    Exception(
      "Invalid move for piece ${piece::class.simpleName} from ${
        move.moves().first().from
      } to ${move.moves().first().to}",
    )

  class MoveToCheck : Exception("Move would put player in check")
}
