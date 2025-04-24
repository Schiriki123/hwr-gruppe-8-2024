package hwr.oop.chess

class Square(val piece : Piece?) {
    fun isEmpty() :  Boolean {
        return piece == null

    }
}