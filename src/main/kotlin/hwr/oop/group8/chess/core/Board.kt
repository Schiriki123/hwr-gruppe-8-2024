package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.Piece

class Board(val fenData: FENData) : BoardInspector {
  private val map = HashMap<Position, Square>()
  var turn: Color
  val enPassant: String
  var castle: String
  var halfmoveClock: Int
  var fullmoveClock: Int
  val boardLogic: BoardLogic = BoardLogic(this)
  val castlingLogic: CastlingLogic = CastlingLogic(this)

  init {
    initializeBoardFromFENString()

    turn = fenData.getTurn()
    castle = fenData.castle
    enPassant = fenData.enPassant
    halfmoveClock = fenData.halfmoveClock
    fullmoveClock = fenData.fullmoveClock
    check(!isCheckmate()) {
      "Game is over, checkmate!"
    }
  }

  private fun initializeBoardFromFENString() {
    for (rank in Rank.entries) {
      val fileIterator = File.entries.iterator()
      fenData.getRank(rank).forEach { character ->
        if (character.isDigit()) {
          repeat(character.digitToInt()) {
            populateSquare(fileIterator, rank, null)
          }
        } else {
          val piece = FENData.createPieceOnBoard(character, this)
          populateSquare(fileIterator, rank, piece)
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }
  }

  private fun populateSquare(
    fileIterator: Iterator<File>,
    rank: Rank,
    piece: Piece?,
  ) {
    check(fileIterator.hasNext()) { "File iterator should have next element." }
    val position = Position(fileIterator.next(), rank)
    map[position] = Square(piece)
  }

  override fun getSquare(position: Position): Square = map.getValue(position)

  fun isSquareEmpty(position: Position): Boolean =
    getSquare(position).getPiece() == null

  override fun findPositionOfPiece(piece: Piece): Position = map.filterValues {
    it.getPiece() === piece
  }.keys.first()

  fun makeMove(move: Move) {
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val specialMove = move.specialMove
    val piece = fromSquare.getPiece()

    checkNotNull(piece)
    check(piece.color == turn) { "It's not your turn" }

    require(piece.color != toSquare.getPiece()?.color) {
      "Cannot move to a square occupied by the same color"
    }

    check(piece.getValidMoveDestinations().contains(move)) {
      "Invalid move for piece ${piece::class.simpleName} from ${move.from} to ${move.to}"
    }

    check(isMoveCheck(move)) { "Move would put player in check" }

    if (!isSquareEmpty(move.to)) resetHalfMoveClock()

    // apply special move
    if (!move.specialMove.isEmpty()) {
      val specialToSquare = getSquare(specialMove.first().to)
      val specialFromSquare = getSquare(specialMove.first().from)
      val specialMovePiece = getPieceAt(specialMove.first().from)
      specialToSquare.setPiece(specialMovePiece)
      specialFromSquare.setPiece(null)
    }

    // apply standard move
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    castlingLogic.updateCastlingPermission()
    try {
      piece.moveCallback(move)
    } catch (e: Exception) {
      // Restore the board state if the moveCallback fails
      fromSquare.setPiece(piece)
      toSquare.setPiece(null)
      throw e
    }
    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  private fun isCheckmate(): Boolean = boardLogic.isCheckmate()

  private fun isMoveCheck(move: Move): Boolean = boardLogic.isMoveCheck(move)

  fun isCheck(): Boolean = boardLogic.isCheck()

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    castlingLogic.isCastlingAllowed(color)

  override fun getCurrentTurn(): Color = turn

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean =
    boardLogic.isPositionThreatened(currentPlayer, position)

  override fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  fun getMap(): HashMap<Position, Square> = map

  fun generateFENBoardString(): String = FENData.generateFENBoardString(this)

  fun getFENData(): FENData = fenData.getFENData(this)
}
