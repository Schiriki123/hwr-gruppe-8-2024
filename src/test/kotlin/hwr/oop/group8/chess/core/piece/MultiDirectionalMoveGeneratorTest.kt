package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MultiDirectionalMoveGeneratorTest : AnnotationSpec() {

  @Test
  fun `Passed directions should be contained`() {
    // given
    val directions = setOf(
      Direction.TOP,
      Direction.LEFT,
      Direction.RIGHT,
      Direction.TOP_LEFT,
    )
    val board = Board(FENData("7k/8/8/8/R7/8/8/K7", 'w', ""))
    val piece = board.getPieceAt(Position(File.A, Rank.FOUR))!!
    // when
    val moveGenerator = MultiDirectionalMoveGenerator(
      piece,
      board,
      directions,
    )
    // then
    assertThat(moveGenerator.directions)
      .containsExactlyInAnyOrderElementsOf(directions)
  }
}
