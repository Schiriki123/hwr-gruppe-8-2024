package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
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
    val move = Move(Position(File.H, Rank.ONE), Position(File.G, Rank.ONE))
    board.makeMove(move)
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("r3k2r/8/8/8/8/8/8/R3K1R1")
    assertThat(board.castle).isEqualTo("Qkq")
  }

  @Test
  fun `Move queen side tower should remove Q from castle`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/R3K2R"))
    val move = Move(Position(File.A, Rank.ONE), Position(File.B, Rank.ONE))
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
      Position(File.E, Rank.ONE),
      Position(File.G, Rank.ONE),
      listOf(
        Move(
          Position(File.H, Rank.ONE),
          Position(File.F, Rank.ONE),
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
      Position(File.E, Rank.ONE),
      Position(File.C, Rank.ONE),
      listOf(
        Move(
          Position(File.A, Rank.ONE),
          Position(File.D, Rank.ONE),
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
      Position(File.E, Rank.EIGHT),
      Position(File.G, Rank.EIGHT),
      listOf(
        Move(
          Position(File.H, Rank.EIGHT),
          Position(File.F, Rank.EIGHT),
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
      Position(File.E, Rank.EIGHT),
      Position(File.C, Rank.EIGHT),
      listOf(
        Move(
          Position(File.A, Rank.EIGHT),
          Position(File.D, Rank.EIGHT),
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
    val move = Move(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(move)
    }
  }

  @Test
  fun `Check that castling is not allowed if piece was moved`() {
    val board = Board(FENData("8/k7/8/8/8/8/8/R3K2R", 'w'))
    // Move the rook
    board.makeMove(Move(Position(File.A, Rank.ONE), Position(File.A, Rank.TWO)))
    // Move opponent piece
    board.makeMove(
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.B, Rank.SEVEN),
      ),
    )
    val startPosition = Position(File.E, Rank.ONE)
    val king = board.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMoveDestinations()

    assertThat(board.castle).isEqualTo("K")
    assertThat(
      possibleMoves,
    ).containsExactlyInAnyOrder(
      Move(startPosition, Position(File.D, Rank.ONE)),
      Move(startPosition, Position(File.D, Rank.TWO)),
      Move(startPosition, Position(File.E, Rank.TWO)),
      Move(startPosition, Position(File.F, Rank.ONE)),
      Move(startPosition, Position(File.F, Rank.TWO)),
      Move(
        startPosition,
        Position(File.G, Rank.ONE),
        listOf(Move(Position(File.H, Rank.ONE), Position(File.F, Rank.ONE))),
      ),
    )
  }

  @Test
  fun `Try to castle from chess`() {
    val board = Board(FENData("8/1k6/4r3/8/8/8/8/R3K2R"))
    val move = Move(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
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
    val move = Move(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
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
    val move = Move(Position(File.E, Rank.ONE), Position(File.C, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Invalid move for piece King from e1 to c1")
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/8/8/8/8/8/8/RN2K2R")
    assertThat(board.castle).isEqualTo("KQkq")
  }
}
