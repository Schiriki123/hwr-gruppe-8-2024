package hwr.oop.group8.chess

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
  fun `Test getting adjacent positions`() {
    val position = Position('d', 4)

    val topPosition = position.getAdjacentPosition(Direction.TOP)
    val bottomPosition = position.getAdjacentPosition(Direction.BOTTOM)
    val leftPosition = position.getAdjacentPosition(Direction.LEFT)
    val rightPosition = position.getAdjacentPosition(Direction.RIGHT)
    val topLeftPosition = position.getAdjacentPosition(Direction.TOP_LEFT)
    val topRightPosition = position.getAdjacentPosition(Direction.TOP_RIGHT)
    val bottomLeftPosition = position.getAdjacentPosition(Direction.BOTTOM_LEFT)
    val bottomRightPosition = position.getAdjacentPosition(Direction.BOTTOM_RIGHT)

    assertThat(topPosition).isEqualTo(Position('d', 5))
    assertThat(bottomPosition).isEqualTo(Position('d', 3))
    assertThat(leftPosition).isEqualTo(Position('c', 4))
    assertThat(rightPosition).isEqualTo(Position('e', 4))
    assertThat(topLeftPosition).isEqualTo(Position('c', 5))
    assertThat(topRightPosition).isEqualTo(Position('e', 5))
    assertThat(bottomLeftPosition).isEqualTo(Position('c', 3))
    assertThat(bottomRightPosition).isEqualTo(Position('e', 3))
  }
}
