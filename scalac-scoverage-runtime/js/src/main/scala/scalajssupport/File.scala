package scalajssupport

import scala.scalajs.js

class File(path: String) {
  val _file = if (!js.Dynamic.global.hasOwnProperty("window").asInstanceOf[Boolean]) new NodeFile(path)
    // else (js.Dynamic.global.window.hasOwnProperty("_phantom").asInstanceOf[Boolean])
    else new PhantomFile(path)
  

  def this(path: String, child: String) = {
    this(File.pathJoin(path, child))
  }

  def delete(): Unit = {
    _file.delete()
  }
  def getAbsolutePath(): String = {
    _file.getAbsolutePath()
  }

  def getName(): String = {
    _file.getName()
  }

  def getPath(): String = {
    _file.getPath()
  }

  def isDirectory(): Boolean = {
    _file.isDirectory()
  }

  def mkdirs(): Unit = {
    _file.mkdirs()
  }

  def listFiles(): Array[File]= {
    _file.listFiles().toArray
  }

  def listFiles(filter: FileFilter): Array[File] = {
    _file.listFiles().filter(filter.accept).toArray
  }

  def readFile(): String = {
    _file.readFile()
  }
}

object File {
  sealed trait JSEnv
  case object Node extends JSEnv
  case object Phantom extends JSEnv

  val jsEnv = if (!js.Dynamic.global.hasOwnProperty("window").asInstanceOf[Boolean])
    Node
  else
    Phantom

  def pathJoin(path: String, child: String): String = 
    jsEnv match {
      case Phantom => PhantomFile.pathJoin(path, child)
      case Node => NodeFile.nodePath.join(path, child)
    }

  def write(path: String, data: String, mode: String = "a") = 
    jsEnv match {
      case Phantom => PhantomFile.write(path, data, mode)
      case Node => NodeFile.fs.writeFileSync(path, data, js.Dynamic.literal(flag=mode))
    }
}