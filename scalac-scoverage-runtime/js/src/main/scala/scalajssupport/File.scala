package scalajssupport

import scala.scalajs.js

class File(path: String) {
  def this(path: String, child: String) = {
    this(File.nodePath.join(path, child))
  }

  def delete(): Unit = {
    if (isDirectory()) File.fs.rmdirSync(path)
    else File.fs.unlinkSync(path)
  }

  def getAbsolutePath(): String = {
    File.fs.realpathSync(path)
  }

  def getName(): String = {
    File.nodePath.basename(path)
  }

  def getPath(): String = {
    path
  }

  def isDirectory(): Boolean = {
    File.fs.lstatSync(path).isDirectory()
  }

  def mkdirs(): Unit = {
    path.split("/").foldLeft("")((acc: String, x: String) => {
      val new_acc = File.nodePath.join(acc, x)
      try {
        File.fs.mkdirSync(new_acc)
      } catch {
        case e: Exception =>
      }
      new_acc
    })
  }

  def listFiles(): Array[File] = {
    // Doesn't seem really efficient, need to check if there's a better way to transform js.Array
    val files = File.fs.readdirSync(path)
    val filesArray = new Array[File](files.length)
    for ((item, i) <- filesArray.zipWithIndex) {
      filesArray(i) = new File(File.nodePath.join(this.getPath(), files(i)))
    }
    filesArray
  }

  def listFiles(filter: FileFilter): Array[File] = {
    listFiles().filter(filter.accept)
  }

  def readFile(): String = {
    File.fs.readFileSync(path)
  }

}

trait FileFilter {
  def accept(file: File): Boolean
}

trait FSStats extends js.Object {
  def isDirectory(): Boolean = js.native
}

trait FS extends js.Object {
  def closeSync(fd: Int): Unit = js.native
  def lstatSync(path: String): FSStats = js.native
  def mkdirSync(path: String): Unit = js.native
  def openSync(path: String, flags: String): Int = js.native
  def realpathSync(path: String): String = js.native
  def readdirSync(path: String): js.Array[String] = js.native
  def readFileSync(path: String): String = js.native
  def rmdirSync(path: String): Unit = js.native
  def unlinkSync(path: String): Unit = js.native
  def writeSync(fd: Int, csq: CharSequence): Unit = js.native

}

trait NodePath extends js.Object {
  def basename(path: String): String = js.native
  def join(paths: String*): String = js.native
}

private[scalajs_support] object File {
  val fs: FS = js.Dynamic.global.require("fs").asInstanceOf[FS]
  val nodePath: NodePath = js.Dynamic.global.require("path").asInstanceOf[NodePath]
}