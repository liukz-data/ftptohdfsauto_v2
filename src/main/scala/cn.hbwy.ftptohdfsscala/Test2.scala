package cn.hbwy.ftptohdfsscala

import cn.hbwy.proutil.PropertiesUtil

object Test2 {
  def main(args: Array[String]): Unit = {
    val versionKeys = PropertiesUtil.properties("G:\\Users\\lkz\\IdeaProjects\\ftptohdfsauto_v2\\src\\main\\scala\\cn.hbwy.ftptohdfsscala\\versionskeys.properties")
   println(versionKeys.size())
    for(i<- 1 to 5){
      println(i)
    }
  }
}
