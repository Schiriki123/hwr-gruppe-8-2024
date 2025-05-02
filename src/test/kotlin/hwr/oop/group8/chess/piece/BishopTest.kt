package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.FENData
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BishopTest : AnnotationSpec() {

  @Test
  fun `Test char representation`() {
    val whiteBishop = Bishop(Color.WHITE)
    val blackBishop = Bishop(Color.BLACK)
    assertThat(whiteBishop.getChar()).isEqualTo('B')
    assertThat(blackBishop.getChar()).isEqualTo('b')
  }

  @Test
  fun `Test bishop movement on empty board`() {
    val board = Board(FENData("B7/8/8/8/8/8/8/8"))
    val move = Move(Position('a', 8), Position('e', 4))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/4B3/8/8/8")
  }

  @Test
  fun `Test bishop movement with blocked path`() {
    val board = Board(FENData("B7/8/8/8/4r3/8/8/8"))
    val move = Move(Position('a', 8), Position('g', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to g2")
  }

  @Test
  fun `Test invalid move`() {
    val board = Board(FENData("B7/8/8/8/4r3/8/8/8"))
    val move = Move(Position('a', 8), Position('a', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to a2")
  }
}
