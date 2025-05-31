package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class CliMoveTest : AnnotationSpec() {
  @Test
  fun `Create CliMove should contain form, to & 'q' as promotion char`() {
    // given
    val from = Position(File.F, Rank.FOUR)
    val to = Position(File.G, Rank.FIVE)
    val promotionChar = 'q'
    // when
    val cliMove = CliMove(from, to, promotionChar)
    // then
    assertThat(cliMove.from).isEqualTo(from)
    assertThat(cliMove.to).isEqualTo(to)
    assertThat(cliMove.promotesTo).isEqualTo(promotionChar)
  }
}
