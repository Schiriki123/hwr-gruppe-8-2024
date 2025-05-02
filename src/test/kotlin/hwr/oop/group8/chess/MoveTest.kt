package hwr.oop.group8.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class MoveTest : AnnotationSpec() {
  @Test
  fun `Test direction for horizontal & vertical moves`() {
    val moveTop = Move(Position('a', 1), Position('a', 2))
    val moveBottom = Move(Position('a', 8), Position('a', 7))
    val moveLeft = Move(Position('b', 2), Position('a', 2))
    val moveRight = Move(Position('a', 2), Position('b', 2))

    assertThat(moveTop.getMoveDirection()).isEqualTo(Direction.TOP)
    assertThat(moveBottom.getMoveDirection()).isEqualTo(Direction.BOTTOM)
    assertThat(moveLeft.getMoveDirection()).isEqualTo(Direction.LEFT)
    assertThat(moveRight.getMoveDirection()).isEqualTo(Direction.RIGHT)
  }

  @Test
  fun `Test direction for diagonal moves`() {
    val moveTopLeft = Move(Position('b', 2), Position('a', 3))
    val moveTopRight = Move(Position('a', 2), Position('b', 3))
    val moveBottomLeft = Move(Position('b', 8), Position('a', 7))
    val moveBottomRight = Move(Position('a', 8), Position('b', 7))

    assertThat(moveTopLeft.getMoveDirection()).isEqualTo(Direction.TOP_LEFT)
    assertThat(moveTopRight.getMoveDirection()).isEqualTo(Direction.TOP_RIGHT)
    assertThat(moveBottomLeft.getMoveDirection()).isEqualTo(Direction.BOTTOM_LEFT)
    assertThat(moveBottomRight.getMoveDirection()).isEqualTo(Direction.BOTTOM_RIGHT)
  }

  @Test
  fun `Test move on spot`() {
    assertThatThrownBy { Move(Position('a', 1), Position('a', 1)).getMoveDirection() }.message()
      .isEqualTo("From and to positions must be different")
  }
}
