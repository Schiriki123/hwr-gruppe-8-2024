package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class RookTest : AnnotationSpec() {
  @Test
  fun `char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteRook = Rook(Color.WHITE, boardInspector)
    val blackRook = Rook(Color.BLACK, boardInspector)
    assertThat(whiteRook.getChar()).isEqualTo('R')
    assertThat(blackRook.getChar()).isEqualTo('r')
  }

  @Test
  fun `Rook movement on empty board from a8 to a2`() {
    val board = Board(FENData("R7/8/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/R7/K7")
  }

  @Test
  fun `invalid rook movement`() {
    val board = Board(FENData("R7/8/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 8), Position('b', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Rook from a8 to b2")
  }

  @Test
  fun `Rook movement with path blocked by pawn`() {
    val board = Board(FENData("R7/8/8/8/8/8/P7/1K6"))
    val move = Move(Position('a', 8), Position('a', 1))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Rook from a8 to a1")
  }

  @Test
  fun `capture piece with rook`() {
    val board = Board(FENData("R7/8/8/8/8/8/p7/K7", 'w', ""))
    val move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/R7/K7")
  }
}
