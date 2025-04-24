package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class BishopTest : AnnotationSpec() {
  @Test
  fun `Test white Bishop creation`() {
    val isWhite = true
    val bishop = Bishop(isWhite)
    assertThat(bishop.isWhite).isTrue
    assertThat(bishop.isCaptured).isFalse
  }

  @Test
  fun `Test Bishop movement from c1`() {
    val isWhite = true
    val bishop = Bishop(isWhite)
    val position = Position('c', 1)
    val moves = bishop.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('b', 2),
      Position('a', 3),
      Position('d', 2),
      Position('e', 3),
      Position('f', 4),
      Position('g', 5),
      Position('h', 6),
    )
  }
}
