package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.piece.Piece

open class IllegalMoveException(message: String) : Exception(message)
class CheckmateException : IllegalMoveException("Game is over, checkmate!")
class DrawException(message: String) : IllegalMoveException(message)
class NoPieceException(move: Move) :
  IllegalMoveException("There is no piece at ${move.moves().first().from}")

class OutOfTurnException : IllegalMoveException("It's not your turn")
class InvalidMoveForPieceException(piece: Piece, move: Move) :
  IllegalMoveException(
    "Invalid move for piece ${piece::class.simpleName} from ${
      move.moves().first().from
    } to ${move.moves().first().to}",
  )

class MoveToCheck : IllegalMoveException("Move would put player in check")
