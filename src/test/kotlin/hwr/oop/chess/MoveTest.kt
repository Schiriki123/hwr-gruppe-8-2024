package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MoveTest : AnnotationSpec() {
  @Test
  fun `Test move creation`() {
    val start = Position('a', 2)
    val end = Position('a', 3)
    val move = Move(start, end)

    assertThat(start).isEqualTo(move.start)
    assertThat(end).isEqualTo(move.end)
  }

  @Test
  fun `test move validation`() {
    val start = Position('a', 2)
    val end = Position('a', 3)
    val move = Move(start, end)

    assertThat(start).isEqualTo(move.start)
    assertThat(end).isEqualTo(move.end)
  }
}
