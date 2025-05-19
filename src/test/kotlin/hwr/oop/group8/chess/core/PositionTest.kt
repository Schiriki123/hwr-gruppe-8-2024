package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PositionTest : AnnotationSpec() {
  @Test
  fun `Test Position initialization`() {
    val file = 'a'
    val rank = 8
    val testPosition = Position(file, rank)

    assertThat(testPosition.rank).isEqualTo(rank)
    assertThat(testPosition.file).isEqualTo(file)
  }

  @Test
  fun `Test invalid position creation`() {
    assertThatThrownBy {
      Position('a', 9)
    }

    assertThatThrownBy {
      Position('a', 0)
    }

    assertThatThrownBy {
      Position('i', 1)
    }
  }

  @Test
  fun `test next position top`() {
    val position = Position('e', 4)
    val direction = Direction.TOP

    val expectedPosition = Position('e', 5)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM

    val expectedPosition = Position('e', 3)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position left`() {
    val position = Position('e', 4)
    val direction = Direction.LEFT

    val expectedPosition = Position('d', 4)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position right`() {
    val position = Position('e', 4)
    val direction = Direction.RIGHT

    val expectedPosition = Position('f', 4)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position top left`() {
    val position = Position('e', 4)
    val direction = Direction.TOP_LEFT

    val expectedPosition = Position('d', 5)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position top right`() {
    val position = Position('e', 4)
    val direction = Direction.TOP_RIGHT

    val expectedPosition = Position('f', 5)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom left`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM_LEFT

    val expectedPosition = Position('d', 3)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `test next position bottom right`() {
    val position = Position('e', 4)
    val direction = Direction.BOTTOM_RIGHT

    val expectedPosition = Position('f', 3)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }
}
