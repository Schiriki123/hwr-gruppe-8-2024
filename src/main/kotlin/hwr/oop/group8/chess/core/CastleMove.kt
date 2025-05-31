package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.King

data class CastleMove(val king: King, val isKingSideCastle: Boolean) : Move {
  val kingColor = king.color
  val homeRank = if (kingColor == Color.WHITE) Rank.ONE else Rank.EIGHT
  override fun moves(): List<SingleMove> = listOf(
    SingleMove(
      Position(File.E, homeRank),
      Position(if (isKingSideCastle) File.G else File.C, homeRank),
    ),
    SingleMove(
      Position(if (isKingSideCastle) File.H else File.A, homeRank),
      Position(if (isKingSideCastle) File.F else File.D, homeRank),
    ),
  )
}
