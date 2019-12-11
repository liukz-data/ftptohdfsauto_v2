package cn.hbwy.proutil

import java.io.FileInputStream
import java.util.Properties

object PropertiesUtil {

  val properties = (proPath:String)=>{
   val proFileInput = new FileInputStream(proPath)
    val pro = new Properties()
    pro.load(proFileInput)
    pro
  }

}
