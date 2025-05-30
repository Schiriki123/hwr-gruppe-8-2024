package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank

class Knight(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val validDestinations: MutableSet<Move> = mutableSetOf()
    val currentPosition = boardInspector.findPositionOfPiece(this)

    val possibleDestination = listOf(
      Pair(2, 1),
      Pair(2, -1),
      Pair(-2, 1),
      Pair(-2, -1),
      Pair(1, 2),
      Pair(1, -2),
      Pair(-1, 2),
      Pair(-1, -2),
    )
    for (pair in possibleDestination) {
      val newFile: File? =
        File.entries.getOrNull(currentPosition.file.value + pair.first)
      val newRank: Rank? =
        Rank.entries.getOrNull(currentPosition.rank.value + pair.second)
      if (newFile != null && newRank != null) {
        val nextPiece = boardInspector.getPieceAt(Position(newFile, newRank))
        // Check if the next position is empty or occupied by an opponent's piece
        if (nextPiece == null || nextPiece.color != color) {
          validDestinations.add(
            Move(
              currentPosition,
              Position(newFile, newRank),
            ),
          )
        }
      }
    }
    return validDestinations
  }

  override fun moveCallback(move: Move) {}

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'N'
    Color.BLACK -> 'n'
  }

  override fun getType(): PieceType = PieceType.KNIGHT
}
