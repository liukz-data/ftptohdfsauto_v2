package cn.hbwy.ftptohdfs.log_util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j文件工具类
 */
public class Log4jUtil {
    /**
     * @param confPath log4j配置文件路径
     * @param clazz
     * @return
     */
    public static Logger getLogger(String confPath, Class clazz) {
        //PropertyConfigurator.configure("G:\\Users\\lkz\\IdeaProjects\\FtpToHdfs1\\src\\main\\scala\\cn\\hbwy\\FtpToHdfs\\sparkdeal\\FileToHive\\log4j.properties");
        PropertyConfigurator.configure(confPath);
        return Logger.getLogger(clazz);
    }
}
