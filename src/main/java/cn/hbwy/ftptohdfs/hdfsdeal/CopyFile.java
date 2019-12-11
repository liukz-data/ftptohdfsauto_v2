package cn.hbwy.ftptohdfs.hdfsdeal;

import cn.hbwy.ftptohdfs.log_util.Log4jUtil;
import cn.hbwy.ftptohdfs.threadpool.MiddleStatus;
import cn.hbwy.ftptohdfs.threadpool.ThreadPool;
import jodd.io.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 找出各个版本的文件，放入结果文件文件夹中
 */
public class CopyFile {
    private String zip_outPath = "/root/temp/extra_out/out";
    private String finalPath = "/root/temp/final/out1";

    public CopyFile() {
    }

    public void copyCsvFile(String zip_outPath, final String finalPath, final String confPath, String ftpfinalpropath) throws IOException {
        File finalOut = new File(finalPath);
        if (finalOut.exists()) {
            FileUtil.delete(finalOut);
        }
        File zipOutPath = new File(zip_outPath);
        String[] zipOutPathRegions = zipOutPath.list();
        FindCsv findCsv = new FindCsv();
        final Logger logger = Log4jUtil.getLogger(confPath, CopyFile.class);
        //文件路径配置文件
        final List<String[]> finalPathStr2s = FinalPath.getFinalPath(ftpfinalpropath);
        ExecutorService executorService = ThreadPool.getThreadPool();
        ArrayList<Future> futures = MiddleStatus.getArray();
        for (final String zipOutPathRegion : zipOutPathRegions) {
            ArrayList<File> files_his = FindCsv.getListFiles();
            files_his.clear();
            String zipOutPathDir = zip_outPath + "/" + zipOutPathRegion;
            ArrayList<File> files = findCsv.getFiles(zipOutPathDir);
            // filesNum = filesNum+files.size();

            for (String[] finalPath2s : finalPathStr2s) {
                File d1 = new File(finalPath + "/" + finalPath2s[0] + "/" + zipOutPathRegion);
                if (!d1.exists()) {
                    d1.mkdirs();
                }
                File f1 = new File(finalPath + "/" + finalPath2s[0] + "/" + zipOutPathRegion + "/reduce_error.csv");
                f1.createNewFile();
            }


            for (final File file : files) {
                Future future = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() {
                        String fileName = file.getName();
                        for (String[] finalPath2s : finalPathStr2s) {
                            if (fileName.matches(finalPath2s[1])) {
                                // System.out.println(file.length()+":"+finalPath + "/ODM-A.WL.PM.FILE_WL_4G_V2.6.0_ENODEB_PM/" +region+"/"+ file.getName());
                                FileOutputStream fileOutputStream = null;
                                logger.info(file.length() + ":" + file.getAbsolutePath());
                                System.out.println(file.length() + ":" + file.getAbsolutePath());
                                try {
                                    File f = new File(finalPath + "/" + finalPath2s[0] + "/" + zipOutPathRegion + "/" + file.getName());
                                    if (!f.exists()) {
                                        f.createNewFile();
                                    }
                                    fileOutputStream = new FileOutputStream(f);
                                    FileUtils.copyFile(file, fileOutputStream);
                                } catch (IOException e) {
                                    logger.error(e);
                                    e.printStackTrace();
                                } finally {
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            } else {
                                logger.info(file.length() + ":" + file.getAbsolutePath());
                                System.out.println("else:" + file.length() + ":" + file.getAbsolutePath());
                            }
                        }
                        return 1;
                    }

                });
                futures.add(future);
            }
        }
    }
}
