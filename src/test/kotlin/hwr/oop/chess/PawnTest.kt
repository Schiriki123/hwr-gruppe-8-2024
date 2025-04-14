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
    val position = Position('b', 2)
    val moves = pawn.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('b', 3), // Move forward one square
      Position('b', 4), // Move forward two squares
      Position('a', 3), // Capture diagonally to the right
      Position('c', 3)  // Capture diagonally to the left
    )
  }
}
