package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position

interface Piece {
  val color: Color
  val boardInspector: BoardInspector

  @Deprecated("Use BoardInspector instead to reference the board")
  fun isMoveValid(move: Move, board: Board): Boolean
  fun getValidMoveDestinations(): Set<Position>
  fun getChar(): Char

  fun myPosition(): Position {
    return boardInspector.findPositionOfPiece(this)
  }
}
