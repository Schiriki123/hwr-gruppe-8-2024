package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class GameTest : AnnotationSpec() {
  @Test
  fun `Create and assert game with default configuration`() {
    // given
    val game = Game(1, FENData())
    // when
    val board = game.board
    // then
    assertThat(
      board.moveHistory,
    ).containsExactly("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR".hashCode())
    assertThat(board.getFENData()).isEqualTo(FENData())
    assertThat(board.generateFENBoardString()).isEqualTo(
      "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
    )
    assertThat(game.id).isEqualTo(1)
  }
}
