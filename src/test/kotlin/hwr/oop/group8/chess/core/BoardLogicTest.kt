package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThatThrownBy

class BoardLogicTest : AnnotationSpec() {
  @Test
  fun `Position should be checkmate`() {
    assertThatThrownBy {
      Board(FEN("k1R5/5R2/8/8/8/8/K7/8", 'b', ""))
    }.message().isEqualTo("Game is over, checkmate!")
  }

  @Test
  fun `Position should not be checkmate, king can capture rook`() {
    Board(FEN("kR6/5R2/8/8/8/8/K7/8", 'b', ""))
  }
}
