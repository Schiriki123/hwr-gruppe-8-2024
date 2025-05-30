package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.Bishop
import hwr.oop.group8.chess.piece.Knight
import hwr.oop.group8.chess.piece.Piece
import hwr.oop.group8.chess.piece.PieceType
import hwr.oop.group8.chess.piece.Queen
import hwr.oop.group8.chess.piece.Rook

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

  fun getSquare(position: Position): Square = map.getValue(position)

  fun isSquareEmpty(position: Position): Boolean =
    getSquare(position).getPiece() == null

  override fun getPieceAt(position: Position): Piece? =
    getSquare(position).getPiece()

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

    var matchingMove = piece.getValidMoveDestinations().find { validMoves ->
      validMoves.moves().first() == move.moves().first()
    }

    checkNotNull(matchingMove) {
      "Invalid move for piece ${piece::class.simpleName} from ${
        move.moves().first().from
      } to ${move.moves().first().to}"
    }

    if (matchingMove.isPromotion()) {
      val promotionType = move.promotesTo()
      checkNotNull(promotionType) {
        "Promotion move must specify a piece type to promote to"
      }
      matchingMove = PromotionMove(
        matchingMove.moves().first().from,
        matchingMove.moves().first().to,
        promotionType,
      )
    }

    check(isMoveCheck(matchingMove)) { "Move would put player in check" }

    if (!isSquareEmpty(move.moves().first().to)) resetHalfMoveClock()

    // apply standard move
    applyMoves(matchingMove)

    if (matchingMove.isPromotion()) {
      val toSquare = getSquare(move.moves().first().to)
      val pieceType = matchingMove.promotesTo()
      requireNotNull(pieceType)
      toSquare.setPiece(generatePromotionPiece(pieceType, piece.color))
    }

    if (piece.getType() == PieceType.PAWN) {
      resetHalfMoveClock()
    }

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  private fun generatePromotionPiece(type: PieceType, color: Color): Piece =
    when (type) {
      PieceType.QUEEN -> Queen(color, this)
      PieceType.ROOK -> Rook(color, this)
      PieceType.BISHOP -> Bishop(color, this)
      PieceType.KNIGHT -> Knight(color, this)
      else -> {
        throw IllegalArgumentException("Invalid promotion piece type: $type")
      }
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
  }

  private fun isCheckmate(): Boolean = boardLogic.isCheckmate()

  private fun isMoveCheck(move: Move): Boolean = boardLogic.isMoveCheck(move)

  fun isCheck(): Boolean = boardLogic.isCheck()

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    castlingLogic.isCastlingAllowed(color)

  override fun getCurrentTurn(): Color = turn

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean =
    boardLogic.isPositionThreatened(currentPlayer, position)

  private fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  fun getMap(): HashMap<Position, Square> = map

  fun generateFENBoardString(): String = FENData.generateFENBoardString(this)

  fun getFENData(): FENData = fenData.getFENData(this)
}
