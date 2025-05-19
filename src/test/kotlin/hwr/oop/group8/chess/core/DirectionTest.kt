package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class DirectionTest : AnnotationSpec() {



  @Test
  fun `Test direction combinations`() {
    val direction1 = Direction.TOP
    val direction2 = Direction.RIGHT

    val newDirection = direction1.combine(direction2)
    assertThat(newDirection).isEqualTo(Direction.TOP_RIGHT)
  }

  @Test
  fun `Test invalid direction combinations`() {
    val direction1 = Direction.TOP
    val direction2 = Direction.TOP_RIGHT

    assertThatThrownBy {
      direction1.combine(direction2)
    }.message().isEqualTo("Invalid direction combination: TOP and TOP_RIGHT")
  }
}
