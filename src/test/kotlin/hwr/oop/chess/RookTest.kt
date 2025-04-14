package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class RookTest : AnnotationSpec() {
  @Test
  fun `Test rook creation`() {
    val isWhite = true
    val rook = Rook(isWhite)
    assertThat(rook.isWhite).isTrue
    assertThat(rook.isCaptured).isFalse
  }

  @Test
  fun `Test rook movement`() {
    val isWhite = true
    val rook = Rook(isWhite)
    val position = Position('d', 4)
    val moves = rook.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position('d', 1),
      Position('d', 2),
      Position('d', 3),
      Position('d', 5),
      Position('d', 6),
      Position('d', 7),
      Position('d', 8),
      Position('a', 4),
      Position('b', 4),
      Position('c', 4),
      Position('e', 4),
      Position('f', 4),
      Position('g', 4),
      Position('h', 4)
    )
  }
}
