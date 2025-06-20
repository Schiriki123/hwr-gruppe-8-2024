package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.piece.Piece

class Castling(val board: Board) {

  fun isAllowed(color: Color): Pair<Boolean, Boolean> {
    val homeRank = if (color == Color.WHITE) Rank.ONE else Rank.EIGHT

    if (board.isCheck()) {
      return Pair(false, false)
    }
    val kingSideAllowed = isKingSideAllowed(homeRank, color)
    val queenSideAllowed = isQueenSideAllowed(homeRank, color)

    return Pair(queenSideAllowed, kingSideAllowed)
  }

  private fun isQueenSideAllowed(homeRank: Rank, color: Color): Boolean {
    val queenSide: Boolean =
      board.isSquareEmpty(Position(File.D, homeRank)) &&
        board.isSquareEmpty(Position(File.C, homeRank)) &&
        board.isSquareEmpty(Position(File.B, homeRank)) &&
        board.castle.contains(if (color == Color.WHITE) "Q" else "q") &&
        !board.isPositionThreatened(color, Position(File.D, homeRank)) &&
        !board.isPositionThreatened(color, Position(File.C, homeRank))
    return queenSide
  }

  private fun isKingSideAllowed(homeRank: Rank, color: Color): Boolean =
    board.isSquareEmpty(Position(File.F, homeRank)) &&
      board.isSquareEmpty(Position(File.G, homeRank)) &&
      board.castle.contains(if (color == Color.WHITE) "K" else "k") &&
      !board.isPositionThreatened(color, Position(File.F, homeRank)) &&
      !board.isPositionThreatened(color, Position(File.G, homeRank))

  fun updatePermission() {
    if (board.castle.isEmpty()) {
      return
    }
    val homeRank =
      if (board.turn == Color.WHITE) Rank.ONE else Rank.EIGHT
    val kingPosition = board.getPieceAt(Position(File.E, homeRank))
    val rookPositionKingSide =
      board.getPieceAt(Position(File.H, homeRank))
    val rookPositionQueenSide =
      board.getPieceAt(Position(File.A, homeRank))
    val kingChar = if (board.turn == Color.WHITE) "K" else "k"
    val queenChar = if (board.turn == Color.WHITE) "Q" else "q"

    if (kingPosition == null) {
      board.castle = board.castle.replace(kingChar, "")
      board.castle = board.castle.replace(queenChar, "")
    }
    if (hasKingSideRookMoved(rookPositionKingSide)) {
      board.castle = board.castle.replace(kingChar, "")
    }
    if (hasQueenSideRookMoved(rookPositionQueenSide)) {
      board.castle = board.castle.replace(queenChar, "")
    }
    if (board.castle.isEmpty()) {
      board.castle = "-"
    }
  }

  private fun hasQueenSideRookMoved(rookPositionQueenSide: Piece?): Boolean =
    rookPositionQueenSide == null || rookPositionQueenSide.color != board.turn

  private fun hasKingSideRookMoved(rookPositionKingSide: Piece?): Boolean =
    rookPositionKingSide == null || rookPositionKingSide.color != board.turn
}
