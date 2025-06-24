package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.exceptions.CheckmateException
import hwr.oop.group8.chess.core.exceptions.InvalidMoveForPieceException
import hwr.oop.group8.chess.core.exceptions.MoveToCheck
import hwr.oop.group8.chess.core.exceptions.NoPieceException
import hwr.oop.group8.chess.core.exceptions.OutOfTurnException
import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Pawn
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook
import hwr.oop.group8.chess.persistence.FEN

class Board(
  private val fen: FEN,
  val stateHistory: List<Int> = listOf(fen.hashOfBoard()),
) {
  val analyser: BoardAnalyser = BoardAnalyser(this, fen.castle) { this.map }
  private var turn: Color = fen.getTurn()
  private var enPassant: Position? = fen.enPassant()
  private var halfmoveClock: Int = fen.halfmoveClock
  private var fullmoveClock: Int = fen.fullmoveClock
  private val map: Map<Position, Square> = buildMap {

    fun putOnSquare(piece: Piece?, rank: Rank, fileIterator: Iterator<File>) {
      require(fileIterator.hasNext()) {
        "File iterator should have next element."
      }
      put(Position(fileIterator.next(), rank), Square(piece))
    }

    fun fenCharToDomain(c: Char, fileIterator: Iterator<File>, rank: Rank) {
      if (c.isDigit()) {
        repeat(c.digitToInt()) {
          putOnSquare(null, rank, fileIterator)
        }
      } else {
        val typeAndColor = FEN.translatePiece(c)
        val p = createPieceOnBoard(typeAndColor.first, typeAndColor.second)
        putOnSquare(p, rank, fileIterator)
      }
    }

    for (rank in Rank.entries) {
      val fileIterator = File.entries.iterator()
      fen.rankRepresentation(rank).forEach { c ->
        fenCharToDomain(c, fileIterator, rank)
      }
    }
  }

  init {
    require(map.size == 64) { "Board must have exactly 64 squares." }
  }

  private fun createPieceOnBoard(type: PieceType, color: Color): Piece =
    when (type) {
      PieceType.PAWN -> Pawn(color, analyser)
      PieceType.ROOK -> Rook(color, analyser)
      PieceType.KNIGHT -> Knight(color, analyser)
      PieceType.BISHOP -> Bishop(color, analyser)
      PieceType.QUEEN -> Queen(color, analyser)
      PieceType.KING -> King(color, analyser)
    }

  private fun applyMoves(move: Move, piece: Piece) {
    move.moves().forEach { applySingleMove(it) }
    if (move.isPromotion()) {
      val toSquare = getSquare(move.moves().first().to)
      val pieceType: PieceType = move.promotesTo()!!
      val promotionPiece = createPieceOnBoard(pieceType, piece.color())
      toSquare.setPiece(promotionPiece)
    }
    enPassant = if (move.isDoublePawnMove()) {
      analyser.allowedEnPassantTarget(move as DoublePawnMove)
    } else {
      null
    }
    if (move.specialCapture() != null) {
      getSquare(move.specialCapture()!!).setPiece(null)
    }
  }

  private fun applySingleMove(singleMove: SingleMove) {
    val toSquare = getSquare(singleMove.to)
    val fromSquare = getSquare(singleMove.from)
    val piece = fromSquare.getPiece()
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    analyser.castling.updatePermission()
  }

  private fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  private fun getSquare(position: Position): Square = map.getValue(position)
  fun castle() = analyser.castling.string()

  fun makeMove(move: Move) {
    analyser.checkForDraw()
    if (analyser.isCheckmate()) throw CheckmateException()

    val piece = analyser.pieceAt(move.moves().first().from)

    if (piece == null) throw NoPieceException(move)
    if (piece.color() != turn) throw OutOfTurnException()

    val selectedMove = piece.validMoves().find { validMoves ->
      validMoves.moves().first() == move.moves()
        .first() &&
        validMoves.promotesTo() == move.promotesTo()
    }

    if (selectedMove == null) {
      throw InvalidMoveForPieceException(
        piece,
        move,
      )
    }

    if (analyser.isMoveCheck(selectedMove)) throw MoveToCheck()

    if (piece.pieceType() == PieceType.PAWN || analyser.isCapture(move)) {
      resetHalfMoveClock()
    }

    applyMoves(selectedMove, piece)

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  fun newStateHistory() = stateHistory.plus(FEN.boardStateHash(this))

  fun turn() = turn
  fun enPassant() = enPassant
  fun halfmoveClock() = halfmoveClock
  fun fullmoveClock() = fullmoveClock
}
