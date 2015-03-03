package scoverage

import scala.scalajs.js



class File(path: String) {
	def this(path: String, child:String) = {
		this(File.path.join(path, child))
	}


	def delete(): Boolean = {
		File.fs.unlinkSync(path)
		true // TODO see above
	}

	def getAbsolutePath(): String = {
		File.fs.realpathSync(path)
	}

  def getName(): String = {
    File.path.basename(path)
  }

  def getPath(): String = {
    path
  }

	def mkdirs(): Boolean = {
		path.split("/").foldLeft("")((acc: String, x:String) => {
			val new_acc = File.path.join(acc, x)
			File.fs.mkdirSync(new_acc)
			new_acc
		})
		true // TODO handle errors
	}

	def listFiles(): Array[File] = {
		// Doesn't seem really efficient, need to check if there's a better way to transform js.Array
		val files = File.fs.readdirSync(path)
		val filesArray = new Array[File](files.length)
		for((item, i) <- filesArray.zipWithIndex) {
			filesArray(i) = new File(files(i))
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
	def accept(file:File): Boolean
}

trait FS extends js.Object {
	def mkdirSync(path:String): Unit = js.native
  def openSync(path: String, flags: String): Int = js.native
	def realpathSync(path: String): String = js.native
	def readdirSync(path: String): js.Array[String] = js.native
  def readFileSync(path: String): String = js.native
  def unlinkSync(path:String): Unit = js.native
  def writeSync(fd: Int, csq: CharSequence): Unit = js.native

}

trait NodePath extends js.Object {
	def basename(path: String): String = js.native
  def join(paths: String*): String = js.native
}

private[scoverage] object File extends js.GlobalScope {
  js.Dynamic.global.require("fs")
  js.Dynamic.global.require("path")
  val fs: FS = js.native
  val path: NodePath = js.native
}