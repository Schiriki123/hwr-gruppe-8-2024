package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class UtilsTest : AnnotationSpec() {

  @Test
  fun `Test conversion from file character to number`() {
    val file = 'b'
    val fileNumber = 1
    val convertedNumber = Utils.covertFileToNumber(file)
    assertThat(convertedNumber).isEqualTo(fileNumber)
  }

  @Test
  fun `Test conversion from number to file Character`() {
    val file = 'b'
    val fileNumber = 1
    val convertedFile = Utils.covertNumberToFile(fileNumber)
    assertThat(convertedFile).isEqualTo(file)
  }
}
