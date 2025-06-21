package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType

class BoardAnalyser(val board: Board) : BoardInspector {
  // TODO: Use BoardInspector interface

  private fun getKingPosition(): Position {
    val allPiecesOfCurrentPlayer = getAllPiecesOfCurrentPlayer()

    return findPositionOfPiece(
      allPiecesOfCurrentPlayer.first {
        it.getType() ==
          PieceType.KING
      },
    )
  }

  private fun isRepetitionDraw(): Boolean =
    board.stateHistory.groupBy { it }.any { it.value.size >= 3 }

  private fun getAllPiecesOfCurrentPlayer(): Set<Piece> =
    board.getMap().values.mapNotNull { it.getPiece() }
      .filter { it.color == board.turn }
      .toSet()

  fun isCheckmate(): Boolean {
    val allPiecesCurrentPlayer = getAllPiecesOfCurrentPlayer()
    allPiecesCurrentPlayer.forEach { piece ->
      val possibleMovesOfPiece = piece.getValidMove()
      possibleMovesOfPiece.forEach { move ->
        if (isMoveCheck(move)) {
          return false
        }
      }
    }
    return true
  }

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean {
    val allPieces: Set<Piece> =
      board.getMap().values.mapNotNull { it.getPiece() }.toSet()
    val possibleMovesOfOpponent: Set<Move> = allPieces
      .filter { it.color != currentPlayer }
      .flatMap { it.getValidMove() }
      .toSet()
    return possibleMovesOfOpponent.any { it.moves().first().to == position }
  }

  fun checkForDraw() {
    if (board.halfmoveClock >= 50) {
      throw IllegalStateException("Game is draw due to the 50-move rule.")
    }
    if (isRepetitionDraw()) {
      throw IllegalStateException("Game is draw due to threefold repetition.")
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

    return !doesMovePutInCheck
  }

  fun isCheck(): Boolean {
    val kingPosition = getKingPosition()

    return isPositionThreatened(board.turn, kingPosition)
  }

  fun isCapture(move: Move): Boolean = !isSquareEmpty(move.moves().first().to)

  override fun getPieceAt(position: Position): Piece? =
    board.getMap().getValue(position).getPiece()

  override fun findPositionOfPiece(piece: Piece): Position =
    board.getMap().filterValues {
      it.getPiece() === piece
    }.keys.first()

  override fun getCurrentTurn(): Color = board.turn
  override fun accessEnPassant(): Position? = board.enPassant
  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    board.isCastlingAllowed(color)
}
