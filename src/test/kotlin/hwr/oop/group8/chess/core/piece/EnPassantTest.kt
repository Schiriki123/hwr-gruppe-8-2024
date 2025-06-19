package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class EnPassantTest : AnnotationSpec() {
  @Test
  @Ignore
  fun `After double pawn move, en passant e6 should be in FEN`() {
    // given
    val board = Board(
      FEN(
        "rnbqkbnr/ppppppp1/8/3P3p/8/8/PPP1PPPP/RNBQKBNR",
        'b',
      ),
    )
    val moveThatAllowsEnPassant = SingleMove(
      Position(File.E, Rank.SEVEN),
      Position(File.E, Rank.FIVE),
    )
    // when
    board.makeMove(moveThatAllowsEnPassant)
    // then
    assertThat(board.enPassant).isEqualTo("e6")
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("rnbqkbnr/pppp1pp1/8/3Pp2p/8/8/PPP1PPPP/RNBQKBNR")
  }
}
