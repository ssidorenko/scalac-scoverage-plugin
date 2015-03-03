package scoverage

import scala.collection.concurrent.TrieMap

class CrossMap[K, V] extends TrieMap[K, V] {}

object CrossMap extends MutableMapFactory[CrossMap] {
	implicit def canBuildFrom[A, B]: CanBuildFrom[CrossMap[A, B], (A, B), CrossMap[A, B]] = new MapCanBuildFrom[A, B]
	def empty[A, B]: CrossMap[A, B] = new CrossMap[A, B]
}