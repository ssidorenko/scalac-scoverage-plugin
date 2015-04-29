package scalajs_support

import scala.io.{ Source => OrigSource }

object Source {
  def fromFile(file: File) = {
    new OrigSource {
      // not efficient at all
      val iter = file.readFile.toCharArray.iterator
    }
  }
}