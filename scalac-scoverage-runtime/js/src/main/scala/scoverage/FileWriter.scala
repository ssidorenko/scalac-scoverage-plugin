package scoverage

class FileWriter(file:File, append:Boolean) {
	import File.fs

	val fd:Int = fs.openSync(file.getPath(), if(append) "a" else "w")
	
	def this(file:File) = this(file, false)
	def this(file:String) = this(new File(file), false)
	def this(file:String, append: Boolean) = this(new File(file), append)

	def append(csq: CharSequence) = {
		fs.writeSync(this.fd, csq)
		this
	}

	def flush() = {}
}
	