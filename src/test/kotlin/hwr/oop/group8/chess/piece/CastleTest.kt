package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class CastleTest : AnnotationSpec() {

  @Suppress("ktlint:standard:max-line-length")
  @Test
  fun `Castling permission is read correctly from castle string for white, castling allowed on first try, second is denied`() {
    val board = Board(
      FENData(
        boardString = "r3k2r/8/8/8/8/8/8/R3K2R",
        castle = "Qkq",
        turn = 'w',
      ),
    )
    val allowedCastlingForWhite = board.isCastlingAllowed(Color.WHITE)
    assertThat(allowedCastlingForWhite.first).isTrue
    assertThat(allowedCastlingForWhite.second).isFalse
  }

  @Suppress("ktlint:standard:max-line-length")
  @Test
  fun `Castling permission is read correctly from castle string for black, castling on first try, second is denied`() {
    val board = Board(
      FENData(
        boardString = "r3k2r/8/8/8/8/8/8/R3K2R",
        castle = "Qk",
        turn = 'b',
      ),
    )
    val allowedCastlingForBlack = board.isCastlingAllowed(Color.BLACK)
    assertThat(allowedCastlingForBlack.first).isFalse
    assertThat(allowedCastlingForBlack.second).isTrue
  }

  @Test
  fun `Move king side tower should remove K from castle`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/R3K2R"))
    val move = Move(Position('h', 1), Position('g', 1))
    board.makeMove(move)
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("r3k2r/8/8/8/8/8/8/R3K1R1")
    assertThat(board.castle).isEqualTo("Qkq")
  }

  @Test
  fun `Move queen side tower should remove Q from castle`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/R3K2R"))
    val move = Move(Position('a', 1), Position('b', 1))
    board.makeMove(move)
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("r3k2r/8/8/8/8/8/8/1R2K2R")
    assertThat(board.castle).isEqualTo("Kkq")
  }

  // KING
  @Test
  fun `Castle king side for white`() {
    val board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    // King side castle
    val move = Move(
      Position('e', 1),
      Position('g', 1),
      listOf(
        Move(
          Position('h', 1),
          Position('f', 1),
        ),
      ),
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/R4RK1")
    assertThat(board.castle).isEqualTo("kq")
  }

  @Test
  fun `Castle queen side for white`() {
    val board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    val move = Move(
      Position('e', 1),
      Position('c', 1),
      listOf(
        Move(
          Position('a', 1),
          Position('d', 1),
        ),
      ),
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/2KR3R")
    assertThat(board.castle).isEqualTo("kq")
  }

  @Test
  fun `Castle king side for black`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/8", 'b'))
    // King side castle
    val move = Move(
      Position('e', 8),
      Position('g', 8),
      listOf(
        Move(
          Position('h', 8),
          Position('f', 8),
        ),
      ),
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("r4rk1/8/8/8/8/8/8/8")
    assertThat(board.castle).isEqualTo("KQ")
  }

  @Test
  fun `Castle queen side for black`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/8", 'b'))
    val move = Move(
      Position('e', 8),
      Position('c', 8),
      listOf(
        Move(
          Position('a', 8),
          Position('d', 8),
        ),
      ),
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("2kr3r/8/8/8/8/8/8/8")
    assertThat(board.castle).isEqualTo("KQ")
  }

  @Test
  fun `Invalid castle with movement through check, expecting exception`() {
    val board = Board(FENData("8/8/5r2/8/8/8/8/R3K2R"))
    val move = Move(Position('e', 1), Position('g', 1))
    assertThatThrownBy {
      board.makeMove(move)
    }
  }

  @Test
  fun `Check that castling is not allowed if piece was moved`() {
    val board = Board(FENData("8/k7/8/8/8/8/8/R3K2R", 'w'))
    // Move the rook
    board.makeMove(Move(Position('a', 1), Position('a', 2)))
    // Move opponent piece
    board.makeMove(Move(Position('a', 7), Position('b', 7)))
    val startPosition = Position('e', 1)
    val king = board.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMoveDestinations()

    assertThat(board.castle).isEqualTo("K")
    assertThat(
      possibleMoves,
    ).containsExactlyInAnyOrder(
      Move(startPosition, Position('d', 1)),
      Move(startPosition, Position('d', 2)),
      Move(startPosition, Position('e', 2)),
      Move(startPosition, Position('f', 1)),
      Move(startPosition, Position('f', 2)),
      Move(
        startPosition,
        Position('g', 1),
        listOf(Move(Position('h', 1), Position('f', 1))),
      ),
    )
  }

  @Test
  fun `Try to castle from chess`() {
    val board = Board(FENData("8/1k6/4r3/8/8/8/8/R3K2R"))
    val move = Move(Position('e', 1), Position('g', 1))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Invalid move for piece King from e1 to g1")
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/1k6/4r3/8/8/8/8/R3K2R")
    assertThat(board.castle).isEqualTo("KQkq")
  }

  @Test
  fun `Try to castle with blocked path king side`() {
    val board = Board(FENData("8/8/8/8/8/8/8/R3KB1R"))
    val move = Move(Position('e', 1), Position('g', 1))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Invalid move for piece King from e1 to g1")
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/8/8/8/8/8/8/R3KB1R")
    assertThat(board.castle).isEqualTo("KQkq")
  }

  @Test
  fun `Try to castle with blocked path queen side`() {
    val board = Board(FENData("8/8/8/8/8/8/8/RN2K2R"))
    val move = Move(Position('e', 1), Position('c', 1))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Invalid move for piece King from e1 to c1")
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/8/8/8/8/8/8/RN2K2R")
    assertThat(board.castle).isEqualTo("KQkq")
  }
}
