package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FileTest : AnnotationSpec() {

  @Test
  fun `File A should be the first file`() {
    // given
    val fileA = File.A
    // when
    val firstFile = File.entries.first()
    // then
    assertThat(firstFile).isEqualTo(fileA)
  }

  @Test
  fun `File H should be the last file`() {
    // given
    val fileH = File.H
    // when
    val lastFile = File.entries.last()
    // then
    assertThat(lastFile).isEqualTo(fileH)
  }

  @Test
  fun `One right from B should be C`() {
    // given
    val fileA = File.B
    // when
    val nextFile = fileA.right()
    // then
    assertThat(nextFile).isEqualTo(File.C)
  }

  @Test
  fun `One left from E should be D`() {
    // given
    val fileH = File.E
    // when
    val previousFile = fileH.left()
    // then
    assertThat(previousFile).isEqualTo(File.D)
  }
}
