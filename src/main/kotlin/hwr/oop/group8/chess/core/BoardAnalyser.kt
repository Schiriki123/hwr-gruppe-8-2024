package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType

class BoardAnalyser(private val board: Board, castle: String) :
  BoardInspector {
  val castling = Castling(this, castle)
  private fun getKingPosition(): Position {
    val allPiecesOfCurrentPlayer = allPieces(board.turn())

    return positionOfPiece(
      allPiecesOfCurrentPlayer.first {
        it.pieceType() ==
          PieceType.KING
      },
    )
  }

  private fun isRepetitionDraw(): Boolean =
    board.stateHistory.groupBy { it }.any { it.value.size >= 3 }

  private fun allPieces(player: Color): Set<Piece> =
    board.map().values.mapNotNull { it.getPiece() }
      .filter { it.color() == player }
      .toSet()

  fun isCheckmate(): Boolean {
    val allPiecesCurrentPlayer = allPieces(board.turn())
    allPiecesCurrentPlayer.forEach { piece ->
      val possibleMovesOfPiece = piece.validMoves()
      possibleMovesOfPiece.forEach { move ->
        if (!isMoveCheck(move)) {
          return false
        }
      }
    }
    return true
  }

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean {
    val allPiecesOfOpponent: Set<Piece> = allPieces(currentPlayer.invert())
    val possibleMovesOfOpponent: Set<Move> =
      allPiecesOfOpponent.flatMap { it.validMoves() }
        .toSet()
    return possibleMovesOfOpponent.any { it.moves().first().to == position }
  }

  fun checkForDraw() {
    if (board.halfmoveClock() >= 50) {
      throw IllegalMove.DrawException("Game is draw due to the 50-move rule.")
    }
    if (isRepetitionDraw()) {
      throw IllegalMove.DrawException(
        "Game is draw due to threefold repetition.",
      )
    }
  }

  fun isMoveCheck(move: Move): Boolean {
    // Simulate the move
    val fromSquare = board.getSquare(move.moves().first().from)
    val toSquare = board.getSquare(move.moves().first().to)
    val movedPiece = fromSquare.getPiece()
    val pieceOnTargetSquare = toSquare.getPiece()
    toSquare.setPiece(movedPiece)
    fromSquare.setPiece(null)

    // Check if the move puts the player in check
    val doesMovePutInCheck = isCheck()

    // Restore original board state
    fromSquare.setPiece(movedPiece)
    toSquare.setPiece(pieceOnTargetSquare)

    return doesMovePutInCheck
  }

  fun isCheck(): Boolean {
    val kingPosition = getKingPosition()

    return isPositionThreatened(board.turn(), kingPosition)
  }

  fun isCapture(move: Move): Boolean = !isSquareEmpty(move.moves().first().to)

  fun allowedEnPassantTarget(move: DoublePawnMove): Position? =
    if (move.to.hasNextPosition(Direction.LEFT) &&
      pieceAt(move.to.left())?.color() != board.turn() &&
      pieceAt(
        move.to.left(),
      )?.pieceType() == PieceType.PAWN
    ) {
      move.skippedPosition()
    } else if (move.to.hasNextPosition(Direction.RIGHT) &&
      pieceAt(move.to.right())?.color() != board.turn() &&
      pieceAt(
        move.to.right(),
      )?.pieceType() == PieceType.PAWN
    ) {
      move.skippedPosition()
    } else {
      null
    }

  override fun pieceAt(position: Position): Piece? =
    board.map().getValue(position).getPiece()

  override fun positionOfPiece(piece: Piece): Position =
    board.map().filterValues {
      it.getPiece() === piece
    }.keys.first()

  override fun currentTurn(): Color = board.turn()
  override fun accessEnPassant(): Position? = board.enPassant()
  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    castling.isAllowed(color)
}
