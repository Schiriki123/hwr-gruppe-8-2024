package hwr.oop.group8.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class DirectionTest : AnnotationSpec() {

  @Test
  fun `test next position top`() {
    val position = Position('e', 4)
    val direction = Direction.TOP

    val expectedPosition = Position('e', 5)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM

    val expectedPosition = Position('e', 3)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position left`() {
    val position = Position('e', 4)
    val direction = Direction.LEFT

    val expectedPosition = Position('d', 4)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position right`() {
    val position = Position('e', 4)
    val direction = Direction.RIGHT

    val expectedPosition = Position('f', 4)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position top left`() {
    val position = Position('e', 4)
    val direction = Direction.TOP_LEFT

    val expectedPosition = Position('d', 5)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position top right`() {
    val position = Position('e', 4)
    val direction = Direction.TOP_RIGHT

    val expectedPosition = Position('f', 5)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom left`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM_LEFT

    val expectedPosition = Position('d', 3)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom right`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM_RIGHT

    val expectedPosition = Position('f', 3)
    val newPosition = direction.nextPosition(position)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

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
