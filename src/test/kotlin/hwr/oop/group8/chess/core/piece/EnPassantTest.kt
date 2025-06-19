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
  fun `After double pawn move of black pawn c6 should be in en passant`() {
    // given
    val board =
      Board(FEN("rnbqkb1r/pppppppp/7n/3P4/8/8/PPP1PPPP/RNBQKBNR", 'b'))
    val moveThatAllowsEnPassant = SingleMove(
      Position(File.C, Rank.SEVEN),
      Position(File.C, Rank.FIVE),
    )
    // when
    board.makeMove(moveThatAllowsEnPassant)
    // then
    assertThat(board.enPassant).isEqualTo(Position(File.C, Rank.SIX))
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("rnbqkb1r/pp1ppppp/7n/2pP4/8/8/PPP1PPPP/RNBQKBNR")
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
  fun `Black En passant move should be allowed, white pawn pawn captured`() {
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

  @Test
  fun `White en passant move should be allowed, black pawn is captured`() {
    // given
    val board = Board(
      FEN(
        "rnbqkb1r/pp1ppppp/7n/1Pp5/8/8/P1PPPPPP/RNBQKBNR",
        enPassant = "c6",
      ),
    )
    val enPassantMove = SingleMove(
      Position(File.B, Rank.FIVE),
      Position(File.C, Rank.SIX),
    )
    // when
    board.makeMove(enPassantMove)
    // then
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("rnbqkb1r/pp1ppppp/2P4n/8/8/8/P1PPPPPP/RNBQKBNR")
    assertThat(board.enPassant).isNull()
  }
}
