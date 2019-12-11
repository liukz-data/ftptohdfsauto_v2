package cn.hbwy.ftptohdfs.zipdeal;


import cn.hbwy.ftptohdfs.log_util.Log4jUtil;
import cn.hbwy.ftptohdfs.threadpool.MiddleStatus;
import cn.hbwy.ftptohdfs.threadpool.ThreadPool;
import jodd.io.FileUtil;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 解压zip
 */
public class ZipDeal {
    private String outPath = "/root/temp/out";
    private String zip_outPath = "/root/temp/extra_out/out";
    private String confPath = "";

    /**
     * @param outPath     下载路径
     * @param zip_outPath zip解压路径
     * @param confPath    log配置文件路径
     */
    public ZipDeal(String outPath, String zip_outPath, String confPath) {
        this.outPath = outPath;
        this.zip_outPath = zip_outPath;
        this.confPath = confPath;
    }

    public void unZip() throws Exception {
        File fileDIr = new File(outPath);
        final Logger logger = Log4jUtil.getLogger(confPath, ZipDeal.class);
        String[] dirNames = fileDIr.list();
        if (dirNames == null) {
            logger.warn("NO FILENAME............SYSTEM EXIT");
            System.exit(-1);
        }
        File zipOut = new File(zip_outPath);
        if (zipOut.exists()) {
            FileUtil.delete(zip_outPath);
        }
        int fileNums = 0;
        ExecutorService pool = ThreadPool.getThreadPool();
        ArrayList<Future> futures = MiddleStatus.getArray();
        for (String dir : dirNames) {
            String dirPartion = outPath + "/" + dir;
            final String zip_out_dir = zip_outPath + "/" + dir;
            File zip_out_dir_file = new File(zip_out_dir);
            File fileDir = new File(dirPartion);
            if (fileDir.isDirectory()) {
                String[] zipFiles = fileDir.list();
                fileNums = fileNums + zipFiles.length;

                for (String fileName : zipFiles) {
                    // System.out.println("UNZIP FILE:  "+fileName);
                    final ZipFile zf = new ZipFile(dirPartion + "/" + fileName);
                    if (!zip_out_dir_file.exists()) {
                        zip_out_dir_file.mkdirs();
                    }

                    Future future = pool.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() {
                            try {
                                zf.extractAll(zip_out_dir);
                                /*int i= MiddleStatus.getNowFiles();
                                MiddleStatus.setNowFiles(i+1);*/
                            } catch (ZipException e) {
                                logger.error(e);
                                e.printStackTrace();
                            }
                            return 1;
                        }
                    });
                    futures.add(future);
                }

            }
        }

    }

}
