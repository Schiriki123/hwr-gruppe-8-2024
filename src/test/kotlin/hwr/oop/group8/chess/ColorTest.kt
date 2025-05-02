package hwr.oop.group8.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class ColorTest : AnnotationSpec() {
  @Test
  fun `test color inversion`() {
    assertThat(Color.WHITE.invert()).isEqualTo(Color.BLACK)
    assertThat(Color.BLACK.invert()).isEqualTo(Color.WHITE)
  }
}
