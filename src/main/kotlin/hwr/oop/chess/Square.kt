package hwr.oop.chess

class Square(val rank: Int, val file: Char, val piece : Piece) {
    fun getPosition(): String{
        return "${file}${rank}"
    }
}