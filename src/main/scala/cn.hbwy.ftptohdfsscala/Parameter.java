package cn.hbwy.ftptohdfsscala;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Parameter {
    private String ftpfinal = "/ftp/vsftpd/hbwy/final";
    private String log4jpath = "/usr/local/program/perform_index/log_conf/log4j.properties";
    private String sqlPath = "";
    private String finalxml = "/ftp/vsftpd/hbwy/final_xml";
    private String ftpfinalpropath;
    private String versionskeyspath;
    private int widefile = 8;
    private int middlefile = 8;
    private int finalfile = 1;

    public Parameter() {

    }

    public void initParameter(String parameterPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(parameterPath);
        Properties pro = new Properties();
        pro.load(fileInputStream);
        ftpfinal = pro.getProperty("ftpfinal");
        log4jpath = pro.getProperty("log4jpath");
        sqlPath = pro.getProperty("sqlpath");
        finalxml = pro.getProperty("finalxml");
        ftpfinalpropath = pro.getProperty("ftpfinalpropath");
        versionskeyspath = pro.getProperty("versionskeyspath");
        widefile = Integer.parseInt(pro.getProperty("widefile"));
        middlefile = Integer.parseInt(pro.getProperty("middlefile"));
        finalfile = Integer.parseInt(pro.getProperty("finalfile"));
    }

    public String getFtpfinal() {
        return ftpfinal;
    }

    public String getLog4jpath() {
        return log4jpath;
    }

    public String getSqlPath() {
        return sqlPath;
    }

    public String getFinalxml() {
        return finalxml;
    }

    public String getFtpfinalpropath() {
        return ftpfinalpropath;
    }

    public String getVersionskeyspath() {
        return versionskeyspath;
    }

    public int getWidefile() {
        return widefile;
    }

    public int getMiddlefile() {
        return middlefile;
    }

    public int getFinalfile() {
        return finalfile;
    }
}
