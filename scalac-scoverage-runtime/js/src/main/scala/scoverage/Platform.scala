package scoverage

import scala.collection.mutable.HashMap
import scala.collection.generic.{ CanBuildFrom, MutableMapFactory }
import scalajs_support.{
  File => SupportFile,
  FileWriter => SupportFileWriter,
  FileFilter => SupportFileFilter,
  Source => SupportSource
}

object Platform {
  type ThreadSafeMap[A, B] = HashMap[A, B]
  lazy val ThreadSafeMap = HashMap

  type File = SupportFile
  type FileWriter = SupportFileWriter
  type FileFilter = SupportFileFilter

  lazy val Source = SupportSource

}