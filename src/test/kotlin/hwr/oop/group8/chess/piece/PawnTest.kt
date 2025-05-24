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
  fun `pawn adds to move history`() {
    val board = Board(FENData("8/8/8/8/8/r7/1P6/K7", 'w', ""))
    val move = Move(Position('b', 2), Position('a', 3))
    val pawn = Pawn(Color.WHITE, board)
    pawn.saveMoveToHistory(move)

    assertThat(pawn.moveHistory.last()).isEqualTo(move)
  }

  @Test
  fun `pawn promotes to queen`() {
    val board = Board(FENData("8/P7/8/8/8/8/8/K7", 'w', ""))
    val move = Move(Position('a', 7), Position('a', 8))
    val pawn: Pawn = board.getPieceAt(Position('a', 7)) as Pawn
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("P7/8/8/8/8/8/8/K7")
    assertThat(pawn.promotedTo).isInstanceOf(Queen::class.java)
  }

  @Test
  fun `pawn with queen promotion and movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    var move = Move(Position('a', 7), Position('a', 8))
    val pawn: Pawn = board.getPieceAt(Position('a', 7)) as Pawn

    //Pawn promotes
    board.makeMove(move)

    //Black Moves
    move = Move(Position('h', 7), Position('h', 6))
    board.makeMove(move)

    // Pawn Moves with Queen movement
    move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/6k1/7p/8/8/8/P7/K7")
    assertThat(pawn.promoted).isTrue
  }

  @Test
  fun `black pawn with knight promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn

    //Pawn promotes
    board.makeMove(move)
    pawn.promotion('n')
    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('f', 2))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/8/5p2/8")
    assertThat(pawn.promoted).isTrue
  }

  @Test
  fun `black pawn with bishop promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn

    //Pawn promotes
    board.makeMove(move)
    pawn.promotion('b')
    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('f', 3))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/5p2/8/8")
    assertThat(pawn.promoted).isTrue
  }

  @Test
  fun `black pawn with rook promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move = Move(Position('h', 2), Position('h', 1))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn

    //Pawn promotes
    board.makeMove(move)
    pawn.promotion('r')
    board.turn = Color.BLACK
    move = Move(Position('h', 1), Position('h', 4))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/7p/8/8/8")
    assertThat(pawn.promoted).isTrue
  }

  @Test
  fun `pawn with illegal knight movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val pawn: Pawn = board.getPieceAt(Position('a', 7)) as Pawn
    pawn.promotion('n')
    pawn.saveMoveToHistory(Move(Position('a', 8), Position('a', 7)))
    val move = Move(Position('a', 7), Position('a', 6))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn with illegal bishop movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val pawn: Pawn = board.getPieceAt(Position('a', 7)) as Pawn
    pawn.promotion('b')
    pawn.saveMoveToHistory(Move(Position('a', 8), Position('a', 7)))
    val move = Move(Position('a', 7), Position('a', 6))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `pawn with illegal rook movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val pawn: Pawn = board.getPieceAt(Position('a', 7)) as Pawn
    pawn.promotion('r')
    pawn.saveMoveToHistory(Move(Position('a', 8), Position('a', 7)))
    val move = Move(Position('a', 7), Position('c', 5))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `black pawn promotes to knight without movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/K7", 'b', ""))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn
    pawn.saveMoveToHistory(Move(Position('a', 8), Position('a', 7)))
    pawn.promotion('n')
    assertThat(pawn.promoted).isTrue
    assertThat(pawn.promotedTo).isInstanceOf(Knight::class.java)
  }

  @Test
  fun `black pawn promotes to bishop without movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/K7", 'b', ""))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn
    pawn.saveMoveToHistory(Move(Position('h', 3), Position('h', 2)))
    pawn.promotion('b')
    assertThat(pawn.promoted).isTrue
    assertThat(pawn.promotedTo).isInstanceOf(Bishop::class.java)
  }

  @Test
  fun `black pawn promotes to rook without movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/K7", 'b', ""))
    val pawn: Pawn = board.getPieceAt(Position('h', 2)) as Pawn
    pawn.saveMoveToHistory(Move(Position('h', 3), Position('h', 2)))
    pawn.promotion('r')
    assertThat(pawn.promoted).isTrue
    assertThat(pawn.promotedTo).isInstanceOf(Rook::class.java)
  }

}
