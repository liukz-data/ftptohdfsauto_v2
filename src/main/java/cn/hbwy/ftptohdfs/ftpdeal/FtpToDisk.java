package cn.hbwy.ftptohdfs.ftpdeal;


import cn.hbwy.ftptohdfs.dateutil.DateUtil;
import cn.hbwy.ftptohdfs.jdbcdeal.GpUtil;
import cn.hbwy.ftptohdfs.log_util.Log4jUtil;
import cn.hbwy.ftptohdfs.maincall.MainCall;
import cn.hbwy.ftptohdfs.orm.FtpHis;
import cn.hbwy.ftptohdfs.threadpool.MiddleStatus;
import cn.hbwy.ftptohdfs.threadpool.ThreadPool;
import jodd.io.FileUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class FtpToDisk {
    private String hostname = "192.168.43.5";
    private int port = 21;
    private String userName = "test1";
    private String passwd = "test1";
    private String ftpWorkDir = "/home/test1";
    private String outPath = "/root/temp/out";
    private String region = "201809261730";
    private int hours = -6;
    private String ftpunzipdir = "/ftp/vsftpd/hbwy/extra";
    private String ftpfinal = "/ftp/vsftpd/hbwy/final";

    public FtpToDisk() {
    }

    public FtpToDisk(String hostname, int port, String userName, String passwd, String ftpWorkDir, String outPath, int hours, String ftpunzipdir, String ftpfinal) {
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
        this.passwd = passwd;
        this.ftpWorkDir = ftpWorkDir;
        this.outPath = outPath;
        this.hours = hours;
        this.ftpunzipdir = ftpunzipdir;
        this.ftpfinal = ftpfinal;
    }

    public void getZipFile(final GpUtil gpUtil, final FTPClientPool ftpClientPool, String sqlstr, String sqlinsert,String log4jpath) throws Exception {
       final Logger logger = Log4jUtil.getLogger(log4jpath, FtpToDisk.class);
        final String[] sqlstrs = sqlstr.split("001");
        final String[] sqlinserts = sqlinsert.split("001");
        File fileOut = new File(outPath);
        if (fileOut.exists()) {
            FileUtil.delete(outPath);
            System.out.println("--------------------------------------");
            System.out.println(outPath + " has been delete!!!");
            System.out.println("--------------------------------------");
        }
        File fileftpunzipdir = new File(ftpunzipdir);
        if (fileftpunzipdir.exists()) {
            FileUtil.delete(ftpunzipdir);
            System.out.println("--------------------------------------");
            System.out.println(ftpunzipdir + " has been delete!!!");
            System.out.println("--------------------------------------");
        }
        File fileftpfinal = new File(ftpfinal);
        if (fileftpfinal.exists()) {
            FileUtil.delete(ftpfinal);
            System.out.println("--------------------------------------");
            System.out.println(ftpfinal + " has been delete!!!");
            System.out.println("--------------------------------------");

        }
        ExecutorService theadPool = ThreadPool.getThreadPool();
        FTPClientUtil ftpClient = null;
        try {
            ftpClient = (FTPClientUtil) ftpClientPool.borrowObject();
            if (!ftpClient.isConnected()) {
                logger.warn("ftpClient IS NOT CONNECT, SYSTEM EXIT!!");
                System.exit(-1);
            }
            String[] fileNames = ftpClient.listNames();
            ArrayList<Future> futures = MiddleStatus.getArray();
            for (final String fileName : fileNames) {
                // if (!fileName.matches("^tpd_eutrancell_q-ODM-.*_NB_V2.9.*\\.zip$")&&fileName.matches("^tpd_eutrancell_q-ODM.*\\.zip$")) {
                String filter_nb = Match.getFilter_nb();
                String filterstart = Match.getFilter_start();
                if (!fileName.matches(filter_nb) && fileName.matches(filterstart)) {

                    Future future = theadPool.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() {
                            FTPClientUtil ftpClient = null;
                            try {
                                ftpClient = (FTPClientUtil) ftpClientPool.borrowObject();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!ftpClient.isConnected()) {
                                logger.warn("ftpClient IS NOT CONNECT, SYSTEM EXIT!!");
                                System.exit(-1);
                            }
                            // fc.enterLocalActiveMode();
                            FileOutputStream out = null;
                            try {
                                String now = DateUtil.getNow();
                                String minus6 = DateUtil.regionMinus6(now, hours);
                                String regionName = getRegion(fileName);
                                if (Long.parseLong(minus6) <= Long.parseLong(regionName)) {

                                    //String sesql = "select his_file,partition_time from hbwy_ftp_his_v3 where partition_time>='" + minus6 + "' and his_file ='" + fileName + "'";
                                    String sesql = sqlstrs[0] + minus6 + sqlstrs[1] + fileName + sqlstrs[2];

                                    ArrayList<FtpHis> FtpHiss = gpUtil.selectFtpHis(sesql);
                                    if (FtpHiss.size() != 0) {
                                        //如果数据库查到此文件，执行下次循环
                                        // System.out.println("FtpHiss.size():" + FtpHiss.size());
                                        return 1;
                                    }

                                    // String insql = "insert into hbwy_ftp_his_v3(his_file,partition_time) values('" + fileName + "','" + regionName + "')";
                                    String insql = sqlinserts[0] + fileName + sqlinserts[1] + regionName + sqlinserts[2];

                                    gpUtil.insertFtpHis(insql);
                                    String outPathRegion = outPath + "/" + regionName;
                                    File file = new File(outPathRegion + "/" + fileName);
                                    File file_d = new File(outPathRegion);
                                    if (!file_d.exists()) {
                                        file_d.mkdirs();
                                    }
                                    if (!file.exists()) {
                                        file.createNewFile();
                                    }
                                    out = new FileOutputStream(file);
                                    ftpClient.retrieveFile(fileName, out);
                                }
                            } catch (Exception e) {
                                logger.error(e);
                                e.printStackTrace();
                            } finally {
                                if (out != null) {
                                    try {
                                        out.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (ftpClient != null) {
                                    ftpClient.close();
                                }
                            }

                            return 1;
                        }
                    });
                    futures.add(future);
                }
            }
        } finally {
            if (ftpClient != null) {
                ftpClient.close();
            }
        }
    }

    public String getRegion(String fileName) {
        String[] splits = fileName.split("-");
        String region = null;
        for (String split : splits) {
            if (split.matches(".*_[0-9]{14}.*")) {
                int startIndex = split.length() - 14;
                int endIndex = split.length() - 2;
                region = split.substring(startIndex, endIndex);

            }
        }
        return region;
    }

    public void setParam(FTPClientConfigure ftpClientConfigure) {
        ftpClientConfigure.setHost(hostname);
        ftpClientConfigure.setClientTimeout(300);
        ftpClientConfigure.setHost(hostname);
        ftpClientConfigure.setPort(port);
        ftpClientConfigure.setPassiveMode("false");
        ftpClientConfigure.setUsername(userName);
        ftpClientConfigure.setPassword(passwd);
        ftpClientConfigure.setWorkPath(ftpWorkDir);
        ftpClientConfigure.setTransferFileType(FTPClient.BINARY_FILE_TYPE);
    }
}
