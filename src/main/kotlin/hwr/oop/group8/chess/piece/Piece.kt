package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

interface Piece {
  val color: Color
  fun isMoveValid(move: Move, board: Board): Boolean
  fun getChar(): Char
}
