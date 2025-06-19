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
    assertThat(board.enPassant).isEqualTo(Position(File.E, Rank.SIX))
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("rnbqkbnr/pppp1pp1/8/3Pp2p/8/8/PPP1PPPP/RNBQKBNR")
  }

  @Test
  fun `After move en passant should be cleared`() {
    // given
    val board = Board(FEN(enPassant = "e6"))
    val move = SingleMove(Position.fromString("a2"), Position.fromString("a3"))
    // when
    board.makeMove(move)
    // then
    assertThat(board.enPassant).isNull()
    assertThat(FEN.getFEN(board).enPassant).isEqualTo("-")
  }

  @Test
  fun `En passant move should be allowed, enemy pawn should be captured`() {
    // given
    val board = Board(
      FEN(
        "rnbqkbnr/ppp1pppp/8/6N1/3pP3/8/PPPP1PPP/RNBQKB1R",
        'b',
        enPassant = "e3",
      ),
    )
    val enPassantCapture = SingleMove(
      Position(File.D, Rank.FOUR),
      Position(File.E, Rank.THREE),
    )
    // when
    board.makeMove(enPassantCapture)
    // then
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("rnbqkbnr/ppp1pppp/8/6N1/8/4p3/PPPP1PPP/RNBQKB1R")
    assertThat(board.enPassant).isNull()
  }
}
