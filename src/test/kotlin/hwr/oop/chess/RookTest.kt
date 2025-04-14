package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec

class RookTest: AnnotationSpec() {
  @Test
  fun `Test rook creation`(){
    val rook = Rook(true)
    assert(rook.isWhite)
    assert(!rook.isCaptured)
  }
}
