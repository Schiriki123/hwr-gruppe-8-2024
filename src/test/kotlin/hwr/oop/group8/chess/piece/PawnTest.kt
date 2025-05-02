package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.FENData
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val whitePawn = Pawn(Color.WHITE)
    val blackPawn = Pawn(Color.BLACK)
    assertThat(whitePawn.getChar()).isEqualTo('P')
    assertThat(blackPawn.getChar()).isEqualTo('p')
  }

  @Test
  fun `Test pawn movement on empty board`() {
    val board = Board(FENData("8/p7/8/8/8/8/8/8"))
    val move = Move(Position('a', 7), Position('a', 6))
//    board.makeMove(move)
//
//    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/8")
  }
}
