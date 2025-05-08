package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Position

interface Piece {
  val color: Color
  val boardInspector: BoardInspector // Remove inspector from interface

  fun getValidMoveDestinations(): Set<Position>
  fun getChar(): Char

  fun myPosition(): Position { // Remove method & implement in methods
    return boardInspector.findPositionOfPiece(this)
  }
}
