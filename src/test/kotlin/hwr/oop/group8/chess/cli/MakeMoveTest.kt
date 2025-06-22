package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.testing.test
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MakeMoveTest : AnnotationSpec() {
  @Test
  fun `Make a move via cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = MakeMove(adapterMock)

    // when
    val args = listOf("1", "e2", "e4")
    val result = cli.test(args)

    // then
    val game = adapterMock.loadGame(1)
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.fen()).isEqualTo(
      FEN("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR", 'b'),
    )
    assertThat(result.stdout).contains("Move made from e2 to e4.")
  }
}
