package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Piece

class BoardLogic(val board: Board) {
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> {
    val homeRank = if (color == Color.WHITE) Rank.ONE else Rank.EIGHT

    if (board.isCheck()) {
      return Pair(false, false)
    }
    val kingSide: Boolean =
      board.isSquareEmpty(Position(File.F, homeRank)) &&
        board.isSquareEmpty(Position(File.G, homeRank)) &&
        board.castle.contains(if (color == Color.WHITE) "K" else "k") &&
        !board.isPositionThreatened(color, Position(File.F, homeRank)) &&
        !board.isPositionThreatened(color, Position(File.G, homeRank))

    // Queen side castle
    val queenSide: Boolean =
      board.isSquareEmpty(Position(File.D, homeRank)) &&
        board.isSquareEmpty(Position(File.C, homeRank)) &&
        board.isSquareEmpty(Position(File.B, homeRank)) &&
        board.castle.contains(if (color == Color.WHITE) "Q" else "q") &&
        !board.isPositionThreatened(color, Position(File.D, homeRank)) &&
        !board.isPositionThreatened(color, Position(File.C, homeRank))
    return Pair(queenSide, kingSide)
  }

  fun updateCastlingPermission() {
    if (board.castle.isEmpty()) {
      return
    }
    val homeRank = if (board.turn == Color.WHITE) Rank.ONE else Rank.EIGHT
    val kingPosition = board.getPieceAt(Position(File.E, homeRank))
    val rookPositionKingSide = board.getPieceAt(Position(File.H, homeRank))
    val rookPositionQueenSide = board.getPieceAt(Position(File.A, homeRank))
    val kingChar = if (board.turn == Color.WHITE) "K" else "k"
    val queenChar = if (board.turn == Color.WHITE) "Q" else "q"

    if (kingPosition == null || kingPosition.color != board.turn) {
      board.castle = board.castle.replace(kingChar, "")
      board.castle = board.castle.replace(queenChar, "")
    }
    if (rookPositionKingSide == null ||
      rookPositionKingSide.color != board.turn
    ) {
      board.castle = board.castle.replace(kingChar, "")
    }
    if (rookPositionQueenSide == null ||
      rookPositionQueenSide.color != board.turn
    ) {
      board.castle = board.castle.replace(queenChar, "")
    }
  }

  fun isMoveCheck(move: Move): Boolean {
    // Simulate the move
    val fromSquare = board.getSquare(move.from)
    val toSquare = board.getSquare(move.to)
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
    val allPieces: Set<Piece> =
      board.getMap().values.mapNotNull { it.getPiece() }.toSet()

    val kingPosition: Position = allPieces.filter { it.color == board.turn }
      .first { it is King }
      .let { board.findPositionOfPiece(it) }

    return board.isPositionThreatened(board.turn, kingPosition)
  }

  fun isCheckmate(): Boolean {
    val allPiecesCurrentPlayer: Set<Piece> =
      board.getMap().values.mapNotNull { it.getPiece() }
        .filter { it.color == board.turn }
        .toSet()
    allPiecesCurrentPlayer.forEach { piece ->
      val possibleMoves: Set<Move> = piece.getValidMoveDestinations()
      // Check if any of the possible moves would put the player in check
      possibleMoves.forEach { destination ->
        if (
          isMoveCheck(
            destination,
          )
        ) {
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
      .flatMap { it.getValidMoveDestinations() }
      .toSet()
    return possibleMovesOfOpponent.any { it.to == position }
  }
}
