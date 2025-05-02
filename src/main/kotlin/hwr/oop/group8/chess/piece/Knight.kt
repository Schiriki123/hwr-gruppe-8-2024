package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

class Knight(override val color: Color) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    TODO("Not yet implemented")
  }

  override fun getChar(): Char {
    return when(color){
      Color.WHITE -> 'N'
      Color.BLACK -> 'n'
    }
  }
}
