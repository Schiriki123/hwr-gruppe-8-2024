package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType

class BoardAnalyser(val board: Board) { // TODO: Use BoardInspector interface

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

    return board.isPositionThreatened(board.turn, kingPosition)
  }

  private fun getKingPosition(): Position {
    val allPiecesOfCurrentPlayer = getAllPiecesOfCurrentPlayer()

    return allPiecesOfCurrentPlayer.first { it.getType() == PieceType.KING }
      .let { board.findPositionOfPiece(it) }
  }

  private fun getAllPiecesOfCurrentPlayer(): Set<Piece> =
    board.getMap().values.mapNotNull { it.getPiece() }
      .filter { it.color == board.turn }
      .toSet()

  fun isCheckmate(): Boolean {
    val allPiecesCurrentPlayer = getAllPiecesOfCurrentPlayer()
    allPiecesCurrentPlayer.forEach { piece ->
      val possibleMoves = piece.getValidMove()
      // Check if any of the possible moves would put the player in check
      possibleMoves.forEach { move ->
        if (isMoveCheck(move)) {
          // If any move is valid and does not put the player in check, return false
          return false
        }
      }
    }
    // If no valid moves are found, return true
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

  private fun isRepetitionDraw(): Boolean =
    board.stateHistory.groupBy { it }.any { it.value.size >= 3 }
}
