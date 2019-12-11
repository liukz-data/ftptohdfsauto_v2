package cn.hbwy.ftptohdfsscala

import java.io.FileInputStream
import java.util.Properties

import scala.collection.mutable.ArrayBuffer

object Test1 {

  def main(args: Array[String]): Unit = {
    val fi = new FileInputStream("H:\\out\\final_xml\\20180926\\201809261730_versions.properties")
    val pro = new Properties()
    pro.load(fi)
    val arrBuff = new ArrayBuffer[String]
   pro.keySet().toArray.foreach(x=>{
    val value =  pro.getProperty(x.toString)
     arrBuff +=value
   })
    println(arrBuff.sorted.reverse)
  }
}
