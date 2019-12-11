package cn.hbwy.ftptohdfs.maincall;

import cn.hbwy.ftptohdfs.dealxml.ForVersion;
import cn.hbwy.ftptohdfs.ftpdeal.*;
import cn.hbwy.ftptohdfs.hdfsdeal.CopyFile;
import cn.hbwy.ftptohdfs.jdbcdeal.GpUtil;
import cn.hbwy.ftptohdfs.log_util.Log4jUtil;
import cn.hbwy.ftptohdfs.threadpool.MiddleStatus;
import cn.hbwy.ftptohdfs.threadpool.ThreadPool;
import cn.hbwy.ftptohdfs.zipdeal.ZipDeal;
import cn.hbwy.ftptohdfsscala.ParameterSql;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 主要的程序入口
 */
public class MainCall {
    public static void main(String[] args) {
        Parameter parameter = new Parameter();
        try {
            parameter.initParameter(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ftpip = parameter.getFtpip();
        int ftpport = parameter.getFtpport();
        String ftpuser = parameter.getFtpuser();
        String ftppasswd = parameter.getFtppasswd();
        String ftpworkdir = parameter.getFtpworkdir();
        String ftploaddir = parameter.getFtploaddir();
        String ftpunzipdir = parameter.getFtpunzipdir();
        String ftpfinal = parameter.getFtpfinal();
        String log4jpath = parameter.getLog4jpath();
        String c3p0path = parameter.getC3p0path();
        int minuxhours = parameter.getMinuxhours();
        String finalXml = parameter.getFinalxml();
        String sqlPath = parameter.getSqlpath();
        String ftpfinalpropath = parameter.getFtpfinalpropath();
        String xmlmatch = parameter.getXmlmatch();
        String sqlstr = parameter.getSqlstr();
        String sqlinsert = parameter.getSqlinsert();
        try {
            ParameterSql.initSql(sqlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String matchpath = parameter.getMatchpath();
        try {
            Match.initMatch(matchpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger logger = Log4jUtil.getLogger(log4jpath, MainCall.class);
        logger.info("   Start Progess......");
        //FtpToDisk ftpToDisk = new FtpToDisk("192.168.43.5",21,"test1","test1","/home/test1/in_zip","/root/temp/out1","201809261730");

        FtpToDisk ftpToDisk = new FtpToDisk(ftpip, ftpport, ftpuser, ftppasswd, ftpworkdir, ftploaddir, minuxhours, ftpunzipdir, ftpfinal);
        FTPClientPool ftpClientPool = null;
        ArrayList<Future> futures = null;
        try {
            FTPClientConfigure ftpClientConfigure = new FTPClientConfigure();
            ftpToDisk.setParam(ftpClientConfigure);
            FtpClientFactory ftpClientFactory = new FtpClientFactory(ftpClientConfigure);
            ftpClientPool = new FTPClientPool(ftpClientFactory);
            GpUtil gpPool = new GpUtil(c3p0path);
            ftpToDisk.getZipFile(gpPool, ftpClientPool, sqlstr, sqlinsert,log4jpath);
            // ftpToDisk.getZipFile(gpPool);
            futures = MiddleStatus.getArray();
            for (Future future : futures) {
                try {
                    future.get(5000l, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            try {
                ftpClientPool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("   Work Path:" + ftpworkdir);
        logger.info("   Zip Path:" + ftploaddir);

        futures.clear();
        logger.info("   LOAD ZIP Complete......");
        // ZipDeal zipDeal = new ZipDeal("/root/temp/out1","/root/temp/extra_out/out1");
        logger.info("   UNZIP Start Progess......");
        ZipDeal zipDeal = new ZipDeal(ftploaddir, ftpunzipdir, log4jpath);
        try {
            zipDeal.unZip();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.info("   Extract Path:" + ftpunzipdir);
        logger.info("   UNZIP Complete......");
        logger.info("   CopyCsv Start Progess......");
        CopyFile copyFile = new CopyFile();
        for (Future future : futures) {
            try {
                future.get(5000l, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        futures.clear();
        try {
            copyFile.copyCsvFile(ftpunzipdir, ftpfinal, log4jpath, ftpfinalpropath);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.info("TOTAL NUMFILES:" + MiddleStatus.getNumFiles() + "----------------------------------");
        for (Future future : futures) {
            try {
                future.get(5000l, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        futures.clear();
        logger.info("NOW NUMFILES:" + MiddleStatus.getNowFiles() + "---------------------------------------------------------------------");
        logger.info("   CopyCsv Complete......");
        logger.info("  Start Copy Xml...");
        ForVersion fv = new ForVersion();
        ArrayList<Future> arrs = null;
        try {
            System.out.println("ftpunzipdir:" + ftpunzipdir);
            System.out.println("finalXml:" + finalXml);
            arrs = fv.copyXmlToOutPath(ftpunzipdir, finalXml, xmlmatch, sqlPath);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        for (Future arr : arrs) {
            try {
                arr.get(5000l, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ThreadPool.getThreadPool().shutdownNow();
        logger.info("  Copy Xml Complete...");
        logger.info("   Stop Progess......");
    }
}
