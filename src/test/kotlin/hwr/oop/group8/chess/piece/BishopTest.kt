package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BishopTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7"))
    val whiteBishop = Bishop(Color.WHITE, boardInspector)
    val blackBishop = Bishop(Color.BLACK, boardInspector)
    assertThat(whiteBishop.getChar()).isEqualTo('B')
    assertThat(blackBishop.getChar()).isEqualTo('b')
  }

  @Test
  fun `Test bishop movement on empty board`() {
    val board = Board(FENData("B7/8/8/8/8/8/8/K7"))
    val move = Move(Position('a', 8), Position('e', 4))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/4B3/8/8/K7")
  }

  @Test
  fun `Test bishop capture move set generation`() {
    val board = Board(FENData("8/8/8/2B5/8/P3p3/8/K7"))
    val validMoveDestinationsOfBishop =
      board.getPieceAt(Position('c', 5))!!.getValidMoveDestinations()

    assertThat(validMoveDestinationsOfBishop).containsExactlyInAnyOrder(
      Position('a', 7),
      Position('b', 6),
      Position('d', 6),
      Position('e', 7),
      Position('f', 8),
      Position('b', 4),
      Position('d', 4),
      Position('e', 3)
    )
  }

  @Test
  fun `Test bishop movement with blocked path`() {
    val board = Board(FENData("B7/8/8/8/4r3/8/8/K7"))
    val move = Move(Position('a', 8), Position('g', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to g2")
  }

  @Test
  fun `Test invalid move`() {
    val board = Board(FENData("B7/8/8/8/4r3/8/8/K7"))
    val move = Move(Position('a', 8), Position('a', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to a2")
  }
}
