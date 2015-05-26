package scalajssupport

trait JsFile {
  def delete(): Unit
  def getAbsolutePath(): String 

  def getName(): String

  def getPath(): String

  def isDirectory(): Boolean

  def mkdirs(): Unit 

  def listFiles(): Array[File] 

  def listFiles(filter: FileFilter): Array[File] = {
    listFiles().filter(filter.accept)
  }

  def readFile(): String 
}

trait FileFilter {
  def accept(file: File): Boolean
}
