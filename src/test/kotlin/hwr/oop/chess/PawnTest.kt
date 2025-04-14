package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test pawn initialization`() {
    val isWhite = true
    val pawn = Pawn(isWhite)
    assertThat(pawn.isWhite).isTrue
    assertThat(pawn.isCaptured).isFalse()
  }

  @Test
  fun `Test pawn movement form b2`() {
    val isWhite = true
    val pawn = Pawn(isWhite)
    val position = Position(2, 'b')
    val moves = pawn.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position(3, 'b'), // Move forward one square
      Position(4, 'b'), // Move forward two squares
      Position(3, 'a'), // Capture diagonally to the right
      Position(3, 'c')  // Capture diagonally to the left
    )
  }
}
