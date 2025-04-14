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
    val position = Position(4, 'd')
    val moves = rook.move(position)

    assertThat(moves).containsExactlyInAnyOrder(
      Position(1, 'd'),
      Position(2, 'd'),
      Position(3, 'd'),
      Position(5, 'd'),
      Position(6, 'd'),
      Position(7, 'd'),
      Position(8, 'd'),
      Position(4, 'a'),
      Position(4, 'b'),
      Position(4, 'c'),
      Position(4, 'e'),
      Position(4, 'f'),
      Position(4, 'g'),
      Position(4, 'h')
    )
  }
}
