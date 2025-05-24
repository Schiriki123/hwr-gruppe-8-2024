package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whitePawn = Pawn(Color.WHITE, boardInspector)
    val blackPawn = Pawn(Color.BLACK, boardInspector)
    assertThat(whitePawn.getChar()).isEqualTo('P')
    assertThat(blackPawn.getChar()).isEqualTo('p')
  }

  @Test
  fun `Test pawn movement block path`() {
    val board = Board(FENData("7k/p7/R7/8/8/8/8/K7", 'b', ""))
    val move = Move(Position('a', 7), Position('a', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("7k/p7/R7/8/8/8/8/K7")
  }

  @Test
  fun `Test pawn movement on empty board`() {
    val board = Board(FENData("8/p7/8/8/8/8/8/k7", 'b', ""))
    val move = Move(Position('a', 7), Position('a', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/k7")
  }

  @Test
  fun `Test backwards pawn movement`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 7))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `Test invalid double move`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `Test white pawn movement on empty board`() {
    val board = Board(FENData("8/8/P7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 7))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/K7")
  }

//  @Test
//  fun `white pawn is not promoted`() {
//    val board = Board(FENData("8/8/P7/8/8/8/8/K7", 'w', ""))
//    val move = Move(Position('a', 6), Position('a', 7))
//    val pawn = board.getPieceAt(Position('a', 6)) as Pawn
//    board.makeMove(move)
//    assertThat(pawn.promoted).isFalse
//    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/K7")
//  }

  @Test
  fun `Test valid double move`() {
    val board = Board(FENData("8/8/8/8/8/8/P7/K7", 'w', ""))
    val move = Move(Position('a', 2), Position('a', 4))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/P7/8/8/K7")
  }

  @Test
  fun `Test valid double move with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/b7/P7/K7", 'w', ""))
    val move = Move(Position('a', 2), Position('a', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/b7/P7/K7")
  }

  @Test
  fun `Test invalid diagonal 2 move with white pawn`() {
    val board = Board(FENData("8/8/8/8/8/1R6/P7/K7", 'w', ""))
    val move = Move(Position('a', 2), Position('c', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1R6/P7/K7")
  }

  @Test
  fun `Test diagonal move without capture`() {
    val board = Board(FENData("8/8/8/8/8/8/1P6/K7", 'w', ""))
    val move = Move(Position('b', 2), Position('c', 3))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/1P6/K7")
  }

  @Test
  fun `Test pawn capture`() {
    val board = Board(FENData("8/8/8/8/8/r7/1P6/K7", 'w', ""))
    val move = Move(Position('b', 2), Position('a', 3))

    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/P7/8/K7")
  }

  @Test
  fun `Test pawn movement with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/p7/P7/K7", 'w', ""))
    val move = Move(Position('a', 2), Position('a', 3))

    assertThatThrownBy { board.makeMove(move) }
  }

  @Test
  fun `pawn promotes to queen`() {
    val board = Board(FENData("8/P7/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('a', 8), promotionChar = 'q')
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/8/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn with queen promotion and movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    var move = Move(Position('a', 7), Position('a', 8), promotionChar = 'q')

    //Pawn promotes
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/6kp/8/8/8/8/8/K7")

    //Black Moves
    move = Move(Position('h', 7), Position('h', 6))
    board.makeMove(move)

    // Pawn Moves with Queen movement
    move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/6k1/7p/8/8/8/Q7/K7")
  }

  @Test
  fun `black pawn with knight promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1), promotionChar = 'n')

    //Pawn promotes
    board.makeMove(move)

    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('f', 2))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/8/5n2/8")
  }

  @Test
  fun `black pawn with bishop promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1), promotionChar = 'b')
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn

    //Pawn promotes
    board.makeMove(move)
    pawn.promotion('b')
    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('f', 3))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/5b2/8/8")
  }

  @Test
  fun `black pawn with rook promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1), promotionChar = 'r')
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn

    //Pawn promotes
    board.makeMove(move)
    pawn.promotion('r')
    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('h', 4))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/7r/8/8/8")
  }

  @Test
  fun `pawn with illegal knight movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('a', 6), promotionChar = 'n')

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn with illegal bishop movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('a', 6), promotionChar = 'b')

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn with illegal rook movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('c', 5), promotionChar = 'r')

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn promotion with illegal character should throw, but original game state is restored`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('a', 8), promotionChar = 'x')

    assertThatThrownBy { board.makeMove(move) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("Invalid promotion piece: x")

    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }
}
