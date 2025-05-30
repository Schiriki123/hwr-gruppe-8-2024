package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.piece.Bishop
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Knight
import hwr.oop.group8.chess.piece.Piece
import hwr.oop.group8.chess.piece.Queen
import hwr.oop.group8.chess.piece.Rook
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class FENDataTest : AnnotationSpec() {
  @Test
  fun `getRank from default board`() {
    val fenData = FENData()

    assertThat(fenData.getRank(Rank.EIGHT)).isEqualTo("rnbqkbnr")
    assertThat(fenData.getRank(Rank.SEVEN)).isEqualTo("pppppppp")
    assertThat(fenData.getRank(Rank.SIX)).isEqualTo("8")
    assertThat(fenData.getRank(Rank.FIVE)).isEqualTo("8")
    assertThat(fenData.getRank(Rank.FOUR)).isEqualTo("8")
    assertThat(fenData.getRank(Rank.THREE)).isEqualTo("8")
    assertThat(fenData.getRank(Rank.TWO)).isEqualTo("PPPPPPPP")
    assertThat(fenData.getRank(Rank.ONE)).isEqualTo("RNBQKBNR")
  }

  @Test
  fun `checking if Instance of an object belongs to the correct object`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8", 'w', ""))
    val pieceChars =
      listOf('r', 'n', 'b', 'q', 'k', 'p', 'R', 'N', 'B', 'Q', 'K', 'P')
    assertThat(pieceChars).allSatisfy { pieceChar ->
      val piece = FENData.createPieceOnBoard(pieceChar, board)
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
      FENData.createPieceOnBoard('x', board)
    }.message().isEqualTo("Invalid piece character: x")
  }

  @Test
  fun `Default board should generate correct fen string`() {
    // given
    val board = Board(FENData())
    // when
    val fenString = FENData.generateFENBoardString(board)
    // then
    assertThat(
      fenString,
    ).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
  }

  @Test
  fun `Invalid initialization, expecting exception`() {
    assertThatThrownBy { FENData("8/8/8/8/8/8/8/8") }.message()
      .isEqualTo("Board string must be 16 or higher")
    assertThatThrownBy { FENData(turn = 'q') }
    assertThatThrownBy { FENData(castle = "KQkb") }
    assertThatThrownBy { FENData(halfmoveClock = -1) }
    assertThatThrownBy { FENData(fullmoveClock = 0) }
  }
}
