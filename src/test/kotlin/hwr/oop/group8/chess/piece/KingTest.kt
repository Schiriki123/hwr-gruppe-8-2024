package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class KingTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteKing = King(Color.WHITE, boardInspector)
    val blackKing = King(Color.BLACK, boardInspector)
    assertThat(whiteKing.getChar()).isEqualTo('K')
    assertThat(blackKing.getChar()).isEqualTo('k')
  }

  @Test
  fun `Test king movement block path`() {
    val board = Board(FENData("8/B7/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 7))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/B7/K7/8/8/8/8/8")
  }

  @Test
  fun `Test king movement on empty board`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    // King moves left
    var move = Move(Position('d', 5), Position('c', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/2K5/8/8/8/8")

    //King moves right
    move = Move(Position('c', 5), Position('d', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")

    //King moves down
    move = Move(Position('d', 5), Position('d', 4))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3K4/8/8/8")

    //King moves up
    move = Move(Position('d', 4), Position('d', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")

    //King moves top right
    move = Move(Position('d', 5), Position('e', 6))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4K3/8/8/8/8/8")

    //King moves top left
    move = Move(Position('e', 6), Position('d', 7))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/3K4/8/8/8/8/8/8")

    //King moves bottom left
    move = Move(Position('d', 7), Position('c', 6))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2K5/8/8/8/8/8")

    //King moves bottom right
    move = Move(Position('c', 6), Position('d', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")
  }

  @Test
  fun `Test invalid double move`() {
    val board = Board(FENData("8/8/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 4))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/K7/8/8/8/8/8")
  }

  @Test
  fun `Test King movement to capture`() {
    val board = Board(FENData("8/p7/1K6/8/8/8/8/8", 'w', ""))
    val move = Move(Position('b', 6), Position('a', 7))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/K7/8/8/8/8/8/8")
  }

  @Test
  fun `Test castle king side for white`() {
    val board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    //King side castle
    val move = Move(
      Position('e', 1), Position('g', 1), listOf(
        Move(
          Position('h', 1),
          Position('f', 1)
        )
      )
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/R4RK1")
    assertThat(board.castle).isEqualTo("kq")
  }

  @Test
  fun `Test castle queen side for white`() {
    val board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    val move = Move(
      Position('e', 1), Position('c', 1), listOf(
        Move(
          Position('a', 1),
          Position('d', 1)
        )
      )
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/2KR3R")
    assertThat(board.castle).isEqualTo("kq")
  }

  @Test
  fun ` castle king side for black`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/8", 'b'))
    //King side castle
    val move = Move(
      Position('e', 8), Position('g', 8), listOf(
        Move(
          Position('h', 8),
          Position('f', 8)
        )
      )
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("r4rk1/8/8/8/8/8/8/8")
    assertThat(board.castle).isEqualTo("KQ")
  }

  @Test
  fun `Test castle queen side for black`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/8", 'b'))
    val move = Move(
      Position('e', 8), Position('c', 8), listOf(
        Move(
          Position('a', 8),
          Position('d', 8)
        )
      )
    )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("2kr3r/8/8/8/8/8/8/8")
    assertThat(board.castle).isEqualTo("KQ")
  }

  @Test
  fun `Test invalid castle with movement through check`() {
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
      possibleMoves
    ).containsExactlyInAnyOrder(
      Move(startPosition, Position('d', 1)),
      Move(startPosition, Position('d', 2)),
      Move(startPosition, Position('e', 2)),
      Move(startPosition, Position('f', 1)),
      Move(startPosition, Position('f', 2)),
      Move(
        startPosition, Position('g', 1),
        listOf(Move(Position('h', 1), Position('f', 1))),
      )
    )
  }

  @Test
  fun `Try to castle from chess`() {
    val board = Board(FENData("8/1k6/4r3/8/8/8/8/R3K2R"))
    val move = Move(Position('e', 1), Position('g', 1))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Invalid move for piece King from e1 to g1")
    assertThat(board.generateFENBoardString()).isEqualTo("8/1k6/4r3/8/8/8/8/R3K2R")
    assertThat(board.castle).isEqualTo("KQkq")
  }

  @Test
  fun `Test movement set generation for black king`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/1K6/8", 'b'))
    val startPosition = Position('e', 8)
    val king = board.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMoveDestinations()

    assertThat(possibleMoves).containsExactlyInAnyOrder(
      Move(startPosition, Position('d', 8)),
      Move(startPosition, Position('d', 7)),
      Move(startPosition, Position('f', 8)),
      Move(startPosition, Position('f', 7)),
      Move(startPosition, Position('e', 7)),
      Move(
        startPosition, Position('c', 8),
        listOf(Move(Position('a', 8), Position('d', 8))),
      ),
      Move(
        startPosition, Position('g', 8),
        listOf(Move(Position('h', 8), Position('f', 8))),
      ),

      )
  }

}
