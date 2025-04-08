package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test pawn initialization`() {
    val isWhite = true
    val pawn = Pawn(isWhite)
    assertThat(pawn.isWhite).isEqualTo(isWhite)
    assertThat(pawn.eliminated).isFalse()
  }

  @Test
  fun `Test pawn movement`(){
  }
}
