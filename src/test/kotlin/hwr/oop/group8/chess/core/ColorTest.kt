package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class ColorTest : AnnotationSpec() {
  @Test
  fun `Color inversion`() {
    assertThat(Color.WHITE.invert()).isEqualTo(Color.BLACK)
    assertThat(Color.BLACK.invert()).isEqualTo(Color.WHITE)
  }
}
