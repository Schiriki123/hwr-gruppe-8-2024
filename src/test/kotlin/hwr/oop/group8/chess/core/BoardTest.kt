package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.*
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BoardTest : AnnotationSpec() {
  @Test
  fun `Test empty board creation`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8"))
    for (rank in 8 downTo 1) {
      for (file in 'a'..'h') {
        val position = Position(file, rank)
        val square = board.getSquare(position)
        if (rank == 8 && file == 'a') {
          square.getPiece().shouldBeInstanceOf<King>()
        } else {
          assertThat(square.getPiece()).isNull()
        }
      }
    }
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/8/8/8")
    assertThat(board.getCapturedPieces()).isEqualTo("White's captures: rnbqkbnrpppppppp${System.lineSeparator()}Black's captures: RNBQBNRPPPPPPPP")
  }

  @Test
  fun `Test board with default setup`() {
    val board = Board(FENData())
    board.getSquare(Position('a', 1)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('b', 1)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('c', 1)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('d', 1)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('e', 1)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('f', 1)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('g', 1)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE
      )
    board.getSquare(Position('h', 1)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE
      )
    for (i in 'a'..'h') {
      board.getSquare(Position(i, 2)).getPiece()
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.WHITE
        )
    }

    // Black pieces
    board.getSquare(Position('a', 8)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('b', 8)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('c', 8)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('d', 8)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('e', 8)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('f', 8)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('g', 8)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK
      )
    board.getSquare(Position('h', 8)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK
      )
    for (i in 'a'..'h') {
      board.getSquare(Position(i, 7)).getPiece()
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.BLACK
        )
    }

    assertThat(board.turn).isEqualTo(Color.WHITE)
    assertThat(board.castle).isEqualTo("KQkq")
    assertThat(board.enPassant).isEqualTo("-")
    assertThat(board.halfmoveClock).isEqualTo(0)
    assertThat(board.fullmoveClock).isEqualTo(1)

    assertThat(board.getCapturedPieces()).isEqualTo("White's captures: ${System.lineSeparator()}Black's captures: ")
  }

  @Test
  fun `Test custom board initialization`() {
    val board = Board(FENData("k7/2R4B/8/8/1q6/8/8/2Q4N", 'b', "", "-", 4, 25))
    board.getSquare(Position('b', 4)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK
      )

    board.getSquare(Position('a', 8)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK
      )

    board.getSquare(Position('h', 7)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE
      )

    board.getSquare(Position('c', 7)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE
      )

    board.getSquare(Position('c', 1)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE
      )

    board.getSquare(Position('h', 1)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE
      )

    assertThat(board.turn).isEqualTo(Color.BLACK)
    assertThat(board.castle).isEmpty()
    assertThat(board.halfmoveClock).isEqualTo(4)
    assertThat(board.fullmoveClock).isEqualTo(25)
  }

  @Test
  fun `Test piece movement`() {
    val board = Board(FENData("8/8/8/8/4R3/8/8/K7"))
    val startPosition = Position('e', 4)
    val endPosition = Position('e', 8)
    val testMove = Move(startPosition, endPosition)

    shouldNotThrowAny {
      board.makeMove(testMove)
    }
  }

  @Test
  fun `Test invalid board creation`() {
    assertThatThrownBy {
      Board(FENData("rnbqkbnr/pppppppp/8/8/8/7/PPPPPPPP/RNBQKBNR"))
    }.message().isEqualTo("Board must have exactly 64 squares.")
    assertThatThrownBy { Board(FENData("8/8/8/8/8/8/8/8p")) }.message()
      .isEqualTo("File must be between 'a' and 'h'")
  }

  @Test
  fun `Test FEN board string creation for default setup`() {
    val board = Board(FENData())
    val fenBoardString = board.generateFENBoardString()
    assertThat(fenBoardString).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
  }

  @Test
  fun `Test FEN board string with custom setup`() {
    val testString = "q4b2/8/8/1Q6/3B4/1PP4K/8/n1n1n1n1"
    val board = Board(FENData(testString))
    val fenBoardString = board.generateFENBoardString()
    assertThat(fenBoardString).isEqualTo(testString)
  }

  @Test
  fun `Test to move piece on own ally`() {
    val board = Board(FENData("K7/8/8/8/8/P7/8/R7"))
    val move = Move(Position('a', 1), Position('a', 3))
    assertThatThrownBy { board.makeMove(move) }.message()
      .isEqualTo("Cannot move to a square occupied by the same color")
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/P7/8/R7") // That board has not changed
  }

  @Test
  fun `Test capture`() {
    val board = Board(FENData("K7/8/8/8/8/p7/8/R7"))
    val move = Move(Position('a', 1), Position('a', 3))
    assertThat(board.getCapturedPieces()).isEqualTo("White's captures: rnbqkbnrppppppp${System.lineSeparator()}Black's captures: NBQBNRPPPPPPPP")
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/R7/8/8")
    assertThat(board.getCapturedPieces()).isEqualTo("White's captures: rnbqkbnrpppppppp${System.lineSeparator()}Black's captures: NBQBNRPPPPPPPP")
  }

  @Test
  fun `Test move in check`() {
    val board = Board(FENData("k7/1R6/8/8/8/8/K7/8", turn = 'b'))
    val move = Move(Position('a', 8), Position('b', 8))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Move would put player in check")
    assertThat(board.generateFENBoardString()).isEqualTo("k7/1R6/8/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }

  @Test
  fun `Test king in check`() {
    val board = Board(FENData("k7/8/R7/8/8/8/K7/8", turn = 'b'))
    val moveToCheck = Move(Position('a', 8), Position('a', 7))
    assertThatThrownBy { board.makeMove(moveToCheck) }.message()
      .isEqualTo("Move would put player in check")

    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/R7/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)

    val validMove = Move(Position('a', 8), Position('b', 7))
    board.makeMove(validMove)
    assertThat(board.generateFENBoardString()).isEqualTo("8/1k6/R7/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.WHITE)
  }

  @Test
  fun `Test move that sets king in check`() {
    val board = Board(FENData("k7/8/r7/8/8/8/R7/8", turn = 'b'))
    val move = Move(Position('a', 6), Position('c', 6))
    assertThatThrownBy { board.makeMove(move) }.message()
      .isEqualTo("Move would put player in check")
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/r7/8/8/8/R7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }

  @Test
  fun `Test for checkmate with valid capture move for escape`() {
    Board(FENData("8/8/8/7R/8/8/5r2/1K5r"))
  }

  @Test
  fun `Test for checkmate with valid move for escape`() {
    Board(FENData("8/8/8/2R5/8/8/5r2/1K5r"))
  }

  @Test
  fun `Test for checkmate with valid King move for escape`() {
    Board(FENData("8/8/8/8/8/8/1r6/1K5r"))
  }

  @Test
  fun `Test for checkmate`() {
    assertThatThrownBy {
      Board(FENData("8/8/8/8/8/8/5r2/1K5r"))
    }.message().isEqualTo("Game is over, checkmate!")
  }

  @Test
  fun `Move king side tower should remove K from castle`() {
    val board = Board(FENData("8/1k6/8/8/8/8/8/R3K2R"))
    val move = Move(Position('h', 1), Position('g', 1))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/1k6/8/8/8/8/8/R3K1R1")
    assertThat(board.castle).isEqualTo("Qkq")
  }
}
