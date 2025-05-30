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
    val fromSquare = getSquare(move.moves().first().from)
    val toSquare = getSquare(move.moves().first().to)
    val piece = fromSquare.getPiece()

    checkNotNull(piece)
    check(piece.color == turn) { "It's not your turn" }

    require(piece.color != toSquare.getPiece()?.color) {
      "Cannot move to a square occupied by the same color"
    }

    val matchingMove = piece.getValidMoveDestinations().find { validMoves ->
      validMoves.moves().first().to == move.moves().first().to &&
        validMoves.moves().first().from == move.moves().first().from
    }

    checkNotNull(matchingMove) {
      "Invalid move for piece ${piece::class.simpleName} from ${
        move.moves().first().from
      } to ${move.moves().first().to}"
    }

    matchingMove.moves().first().promotionChar =
      move.moves().first().promotionChar

    check(isMoveCheck(matchingMove)) { "Move would put player in check" }

    if (!isSquareEmpty(move.moves().first().to)) resetHalfMoveClock()

    // apply standard move
    applyMoves(matchingMove)

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  private fun applyMoves(move: Move) {
    move.moves().forEach { applySingleMove(it) }
  }

  private fun applySingleMove(singleMove: SingleMove) {
    val toSquare = getSquare(singleMove.to)
    val fromSquare = getSquare(singleMove.from)
    val piece = fromSquare.getPiece()
    checkNotNull(piece) { "No piece found at ${singleMove.from}" }
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    castlingLogic.updateCastlingPermission()
    try {
      piece.moveCallback(singleMove)
    } catch (e: Exception) {
      // Restore the board state if the moveCallback fails
      fromSquare.setPiece(piece)
      toSquare.setPiece(null)
      throw e
    }
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
