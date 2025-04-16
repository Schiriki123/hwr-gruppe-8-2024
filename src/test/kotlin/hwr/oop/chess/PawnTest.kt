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
  fun `Test white pawn movement form b2`() {
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

  @Test
  fun `Test black pawn movement from f7`() {
    val isWhite = false
    val pawn = Pawn(isWhite)
    val position = Position('f', 7)
    val moves = pawn.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('f', 6), // Move forward one square
      Position('f', 5), // Move forward two squares
      Position('e', 6), // Capture diagonally to the right
      Position('g', 6)  // Capture diagonally to the left
    )
  }

  @Test
  fun `Test Black pawn movement from 7a`() {
    val isWhite = false
    val pawn = Pawn(isWhite)
    val position = Position('a', 7)
    val moves = pawn.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('a', 6), // Move forward one square
      Position('a', 5), // Move forward two squares
      Position('b', 6)  // Capture diagonally to the right
    )
  }

  @Test
  fun `Test white pawn movement from h6`() {
    val isWhite = true
    val pawn = Pawn(isWhite)
    val position = Position('h', 6)
    val moves = pawn.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('h', 7),
      Position('g', 7)
    )
  }
}
