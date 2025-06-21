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
    update(board.turn)
    update(board.turn.invert())
  }

  private fun update(turn: Color) {
    val homeRank =
      if (turn == Color.WHITE) Rank.ONE else Rank.EIGHT
    val kingPosition =
      board.analyser.getPieceAt(Position(File.E, homeRank))
    val rookPositionKingSide =
      board.analyser.getPieceAt(Position(File.H, homeRank))
    val rookPositionQueenSide =
      board.analyser.getPieceAt(Position(File.A, homeRank))
    val kingChar = if (turn == Color.WHITE) "K" else "k"
    val queenChar = if (turn == Color.WHITE) "Q" else "q"

    if (kingPosition == null) {
      board.castle = board.castle.replace(kingChar, "")
      board.castle = board.castle.replace(queenChar, "")
    }
    if (hasKingSideRookMoved(rookPositionKingSide, turn)) {
      board.castle = board.castle.replace(kingChar, "")
    }
    if (hasQueenSideRookMoved(rookPositionQueenSide, turn)) {
      board.castle = board.castle.replace(queenChar, "")
    }
    if (board.castle.isEmpty()) {
      board.castle = "-"
    }
  }

  private fun hasQueenSideRookMoved(
    rookPositionQueenSide: Piece?,
    turn: Color,
  ): Boolean =
    rookPositionQueenSide == null || rookPositionQueenSide.color != turn

  private fun hasKingSideRookMoved(
    rookPositionKingSide: Piece?,
    turn: Color,
  ): Boolean =
    rookPositionKingSide == null || rookPositionKingSide.color != turn
}
