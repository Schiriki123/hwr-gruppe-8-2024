package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class FENTest : AnnotationSpec() {
  @Test
  fun `getRank from default board`() {
    val fen = FEN()

    assertThat(fen.getRank(Rank.EIGHT)).isEqualTo("rnbqkbnr")
    assertThat(fen.getRank(Rank.SEVEN)).isEqualTo("pppppppp")
    assertThat(fen.getRank(Rank.SIX)).isEqualTo("8")
    assertThat(fen.getRank(Rank.FIVE)).isEqualTo("8")
    assertThat(fen.getRank(Rank.FOUR)).isEqualTo("8")
    assertThat(fen.getRank(Rank.THREE)).isEqualTo("8")
    assertThat(fen.getRank(Rank.TWO)).isEqualTo("PPPPPPPP")
    assertThat(fen.getRank(Rank.ONE)).isEqualTo("RNBQKBNR")
  }

  @Test
  fun `checking if Instance of an object belongs to the correct object`() {
    val board = Board(FEN("K7/8/8/8/8/8/8/8", 'w', ""))
    val pieceChars =
      listOf('r', 'n', 'b', 'q', 'k', 'p', 'R', 'N', 'B', 'Q', 'K', 'P')
    assertThat(pieceChars).allSatisfy { pieceChar ->
      val piece = FEN.createPieceOnBoard(pieceChar, board)
      when (pieceChar) {
        'r', 'R' -> piece.shouldBeInstanceOf<Rook>()
        'n', 'N' -> piece.shouldBeInstanceOf<Knight>()
        'b', 'B' -> piece.shouldBeInstanceOf<Bishop>()
        'q', 'Q' -> piece.shouldBeInstanceOf<Queen>()
        'k', 'K' -> piece.shouldBeInstanceOf<King>()
        'p', 'P' -> piece.shouldBeInstanceOf<Piece>()
      }
    }
    assertThatThrownBy {
      FEN.createPieceOnBoard('x', board)
    }.message().isEqualTo("Invalid piece character: x")
  }

  @Test
  fun `Default board should generate correct fen string`() {
    // given
    val board = Board(FEN())
    // when
    val fenString = FEN.generateFENBoardString(board)
    // then
    assertThat(
      fenString,
    ).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
  }

  @Test
  fun `Invalid initialization, expecting exception`() {
    assertThatThrownBy { FEN("8/8/8/8/8/8/8/8") }.message()
      .isEqualTo("Board string must be 16 or higher")
    assertThatThrownBy { FEN(turn = 'q') }
    assertThatThrownBy { FEN(castle = "KQkb") }
    assertThatThrownBy { FEN(halfmoveClock = -1) }
    assertThatThrownBy { FEN(fullmoveClock = 0) }
  }

  @Test
  fun `En passant null on board should be '-' in FEN`() {
    // given
    val board = Board(FEN(enPassant = "-"))
    assertThat(board.enPassant).isNull()
    // when
    val fen = FEN.getFEN(board)
    // then
    assertThat(fen.enPassant).isEqualTo("-")
  }
}
