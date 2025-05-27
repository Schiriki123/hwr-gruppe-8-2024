package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec

class FileTest : AnnotationSpec() {

  @Test
  fun `File A should be the first file`() {
    // given
    val fileA = File.A
    // when
    val isFirstFile = fileA == File.entries.first()
    // then
    assert(isFirstFile) {
      "Expected File.A to be the first file, but it is not."
    }
  }

  @Test
  fun `File H should be the last file`() {
    // given
    val fileH = File.H
    // when
    val isLastFile = fileH == File.entries.last()
    // then
    assert(isLastFile) {
      "Expected File.H to be the last file, but it is not."
    }
  }

  @Test
  fun `One right from B should be C`() {
    // given
    val fileA = File.B
    // when
    val nextFile = fileA.right()
    // then
    assert(nextFile == File.C) {
      "Expected next file from File.A to be File.B, but got $nextFile"
    }
  }

  @Test
  fun `One left from E should be D`() {
    // given
    val fileH = File.E
    // when
    val previousFile = fileH.left()
    // then
    assert(previousFile == File.D) {
      "Expected previous file from File.H to be File.G, but got $previousFile"
    }
  }
}
