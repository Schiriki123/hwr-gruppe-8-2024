package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.piece.PieceType
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class FENTest : AnnotationSpec() {
  @Test
  fun `getRank from default board`() {
    val fen = FEN()

    assertThat(fen.rankRepresentation(Rank.EIGHT)).isEqualTo("rnbqkbnr")
    assertThat(fen.rankRepresentation(Rank.SEVEN)).isEqualTo("pppppppp")
    assertThat(fen.rankRepresentation(Rank.SIX)).isEqualTo("8")
    assertThat(fen.rankRepresentation(Rank.FIVE)).isEqualTo("8")
    assertThat(fen.rankRepresentation(Rank.FOUR)).isEqualTo("8")
    assertThat(fen.rankRepresentation(Rank.THREE)).isEqualTo("8")
    assertThat(fen.rankRepresentation(Rank.TWO)).isEqualTo("PPPPPPPP")
    assertThat(fen.rankRepresentation(Rank.ONE)).isEqualTo("RNBQKBNR")
  }

  @Test
  fun `checking if Instance of an object belongs to the correct object`() {
    val pieceChars =
      listOf('r', 'n', 'b', 'q', 'k', 'p', 'R', 'N', 'B', 'Q', 'K', 'P')
    assertThat(pieceChars).allSatisfy { pieceChar ->
      val piece = FEN.translatePiece(pieceChar)
      when (pieceChar) {
        'r', 'R' -> piece.first.shouldBe(PieceType.ROOK)
        'n', 'N' -> piece.first.shouldBe(PieceType.KNIGHT)
        'b', 'B' -> piece.first.shouldBe(PieceType.BISHOP)
        'q', 'Q' -> piece.first.shouldBe(PieceType.QUEEN)
        'k', 'K' -> piece.first.shouldBe(PieceType.KING)
        'p', 'P' -> piece.first.shouldBe(PieceType.PAWN)
      }
    }
    assertThatThrownBy {
      FEN.translatePiece('x')
    }.message().isEqualTo("Invalid piece character: x")
  }

  @Test
  fun `Default board should generate correct fen string`() {
    // given
    val board = Board.factory(FEN())
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
    val board = Board.factory(FEN(enPassant = "-"))
    assertThat(board.enPassant()).isNull()
    // when
    val fen = FEN.to(board)
    // then
    assertThat(fen.enPassant()).isNull()
  }
}
