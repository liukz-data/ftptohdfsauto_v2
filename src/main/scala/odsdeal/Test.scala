package odsdeal

import scala.collection.mutable.ArrayBuffer

object Test {

  def main(args: Array[String]): Unit = {
    val arr=ArrayBuffer[String]()
    arr ++=Array("a","b")
    val arr1=ArrayBuffer[String]()
    arr1 ++=Array("c","b")
    print(arr.indexOf("b"))
    arr1.update(1,"eeeee")
    print(arr1)
  }
}
