package cn.hbwy.ftptohdfs.maincall;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件的javabean
 */
public class Parameter {
    private String ftpip = "10.216.3.192";
    private int ftpport = 21;
    private String ftpuser = "ftpuser";
    private String ftppasswd = "bjxh6Yz+";
    private String ftpworkdir = "/opt/ftpuser/zhjk_15kpi";
    private String ftploaddir = "/ftp/vsftpd/hbwy/zip";
    private String ftpunzipdir = "/ftp/vsftpd/hbwy/extra";
    private String ftpfinal = "/ftp/vsftpd/hbwy/final";
    private String log4jpath = "/usr/local/program/perform_index/log_conf/log4j.properties";
    private String c3p0path = "/usr/local/program/perform_index/log_conf/c3p0.properties";
    private int minuxhours = -6;
    private String finalxml = "/ftp/vsftpd/hbwy/final_xml";
    private String sqlpath = "";
    private String matchpath = "";
    private String ftpfinalpropath;
    private String xmlmatch;
    private String sqlstr;
    private String sqlinsert;

    public Parameter() {

    }

    public void initParameter(String parameterPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(parameterPath);
        Properties pro = new Properties();
        pro.load(fileInputStream);
        ftpip = pro.getProperty("ftpip");
        ftpport = Integer.parseInt(pro.getProperty("ftpport"));
        ftpuser = pro.getProperty("ftpuser");
        ftppasswd = pro.getProperty("ftppasswd");
        ftpworkdir = pro.getProperty("ftpworkdir");
        ftploaddir = pro.getProperty("ftploaddir");
        ftpunzipdir = pro.getProperty("ftpunzipdir");
        ftpfinal = pro.getProperty("ftpfinal");
        log4jpath = pro.getProperty("log4jpath");
        c3p0path = pro.getProperty("c3p0path");
        minuxhours = Integer.parseInt(pro.getProperty("minuxhours"));
        finalxml = pro.getProperty("finalxml");
        sqlpath = pro.getProperty("sqlpath");
        matchpath = pro.getProperty("matchpath");
        ftpfinalpropath = pro.getProperty("ftpfinalpropath");
        xmlmatch = pro.getProperty("xmlmatch");
        sqlstr = pro.getProperty("sqlstr");
        sqlinsert = pro.getProperty("sqlinsert");
        fileInputStream.close();
    }

    public String getFtpip() {
        return ftpip;
    }

    public int getFtpport() {
        return ftpport;
    }

    public String getFtpuser() {
        return ftpuser;
    }

    public String getFtppasswd() {
        return ftppasswd;
    }

    public String getFtpworkdir() {
        return ftpworkdir;
    }

    public String getFtploaddir() {
        return ftploaddir;
    }

    public String getFtpunzipdir() {
        return ftpunzipdir;
    }

    public String getFtpfinal() {
        return ftpfinal;
    }

    public String getLog4jpath() {
        return log4jpath;
    }

    public String getC3p0path() {
        return c3p0path;
    }

    public int getMinuxhours() {
        return minuxhours;
    }

    public String getFinalxml() {
        return finalxml;
    }

    public String getSqlpath() {
        return sqlpath;
    }

    public String getMatchpath() {
        return matchpath;
    }

    public String getFtpfinalpropath() {
        return ftpfinalpropath;
    }

    public String getXmlmatch() {
        return xmlmatch;
    }

    public String getSqlstr() {
        return sqlstr;
    }

    public String getSqlinsert() {
        return sqlinsert;
    }
}
