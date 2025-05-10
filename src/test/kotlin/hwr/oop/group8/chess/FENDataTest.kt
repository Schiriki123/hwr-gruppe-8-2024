package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.*
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class FENDataTest : AnnotationSpec() {
  @Test
  fun `test getRank for default board`() {
    val fenData = FENData()
    val expectedRanks = listOf(
      "rnbqkbnr",
      "pppppppp",
      "8",
      "8",
      "8",
      "8",
      "PPPPPPPP",
      "RNBQKBNR"
    )

    for (i in 1..8) {
      assertThat(fenData.getRank(i)).isEqualTo(expectedRanks[i - 1])
    }

    assertThatThrownBy {
      fenData.getRank(9)
    }.message().isEqualTo("Rank must be between 1 and 8")
    assertThatThrownBy {
      fenData.getRank(0)
    }.message().isEqualTo("Rank must be between 1 and 8")
  }

  @Test
  fun `test piece object creation`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8"))
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
  fun `Test invalid initialization`() {
    assertThatThrownBy { FENData("t") }
    assertThatThrownBy { FENData(turn = 'q') }
    assertThatThrownBy { FENData(castle = "KQkb") }
    assertThatThrownBy { FENData(halfmoveClock = -1) }
    assertThatThrownBy { FENData(fullmoveClock = 0) }
  }
}
