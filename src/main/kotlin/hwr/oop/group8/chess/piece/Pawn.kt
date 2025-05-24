package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position

class Pawn(
  override val color: Color,
  val boardInspector: BoardInspector,
  override val moveHistory: MutableList<Move> = mutableListOf(),
  var promoted: Boolean = false,
) : Piece {
  var promotedTo: Piece

  init {
    promotedTo = this
  }

  fun getPosition(): Position {
    return boardInspector.findPositionOfPiece(this)
  }

  override fun getValidMoveDestinations(): Set<Move> {
    var validMoves: MutableSet<Move> = mutableSetOf()
    val forwardDirection: Direction
    val startRank: Int
    val currentPosition = boardInspector.findPositionOfPiece(this)

    if (color == Color.WHITE) {
      forwardDirection = Direction.TOP
      startRank = 2
    } else {
      forwardDirection = Direction.BOTTOM
      startRank = 7
    }

    if (!promoted) {// Check for straight move
      val nextField =
        getPosition().nextPosition(forwardDirection)
      if (boardInspector.getPieceAt(nextField) == null) {
        validMoves.add(Move(currentPosition, nextField))
        // Check for double move from starting position
        if (getPosition().rank == startRank) {
          val twoSquaresForward = nextField.nextPosition(forwardDirection)
          if (boardInspector.getPieceAt(twoSquaresForward) == null) {
            validMoves.add(Move(currentPosition, twoSquaresForward))
          }
        }
      }

      // Check for diagonal captures
      for (direction in setOf(
        Direction.LEFT.combine(forwardDirection),
        Direction.RIGHT.combine(forwardDirection)
      )) {
        if (getPosition().hasNextPosition(direction)) {
          val nextPosition = getPosition().nextPosition(direction)
          val nextPiece = boardInspector.getPieceAt(nextPosition)
          if (nextPiece != null && nextPiece.color != color) {
            validMoves.add(Move(currentPosition, nextPosition))
          }
        }
      }
    } else {
      validMoves = promotedTo.getValidMoveDestinations().toMutableSet()
    }

    return validMoves.toSet()
  }

  override fun saveMoveToHistory(move: Move) {
    if (!promoted && move.to.rank == 8 || move.to.rank == 1) {
      promotion('q')
    }
    this.moveHistory.add(move)
    if (promoted) {
      promotedTo.moveHistory.clear()
      promotedTo.moveHistory.addAll(moveHistory)
    }
  }

  fun promotion(promoteTo: Char) {
    promoted = true
    val pawnMoveHistory = mutableListOf<Move>()
    pawnMoveHistory.addAll(moveHistory)
    when (promoteTo) {
      'q', 'Q' -> promotedTo = Queen(color, boardInspector, pawnMoveHistory)
      'n', 'N' -> promotedTo = Knight(color, boardInspector, pawnMoveHistory)
      'r', 'R' -> promotedTo = Rook(color, boardInspector, pawnMoveHistory)
      'b', 'B' -> promotedTo = Bishop(color, boardInspector, pawnMoveHistory)
    }
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'P'
      Color.BLACK -> 'p'
    }
  }
}
