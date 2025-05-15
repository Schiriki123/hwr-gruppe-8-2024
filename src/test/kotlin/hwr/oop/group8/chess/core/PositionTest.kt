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
}
