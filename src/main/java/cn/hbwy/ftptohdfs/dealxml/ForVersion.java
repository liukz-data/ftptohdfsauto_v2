package cn.hbwy.ftptohdfs.dealxml;

import cn.hbwy.ftptohdfs.ParseXml.ParseXml;
import cn.hbwy.ftptohdfs.hdfsdeal.FindCsv;
import cn.hbwy.ftptohdfs.threadpool.ThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ForVersion {
    public ArrayList<Future> copyXmlToOutPath(String regionParentPath, final String outPath, String xmlmatch, final String sqlPath) throws IOException {
        ArrayList futrues = new ArrayList();
        File regionParentDir = new File(regionParentPath);
        File[] regionFileDirs = regionParentDir.listFiles();
        if (regionFileDirs == null) {
            System.out.println("CLASS ForVersion IS NULL , RegionFileDirs is null");
            System.exit(-1);
        }
        ExecutorService pool = ThreadPool.getThreadPool();
        final List<String> xmlPros = XmlMatch.getMatchXml(xmlmatch);
        for (final File regionFileDir : regionFileDirs) {
            String regionPath = regionFileDir.getAbsolutePath();
            FindCsv findXml = new FindCsv();
            final ArrayList<File> versionDirs = findXml.getXmlFiles(regionPath);
            if (versionDirs.size() == 0) {
                return futrues;
            }
            Future future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    String regionName = regionFileDir.getName();
                    String dayName = regionName.substring(0, 8);
                    String outXmlRegion = outPath + "/" + dayName;
                    File outXmlRegionDir = new File(outXmlRegion);
                    if (!outXmlRegionDir.exists()) {
                        outXmlRegionDir.mkdirs();
                    }
                    String outProPath = outPath + "/" + dayName + "/" + regionName + "_versions.properties";
                    File outProFile = new File(outProPath);
                    if (!outProFile.exists()) {
                        try {
                            outProFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Properties versionPro = new Properties();
                    for (File versionDir : versionDirs) {
                        String versionName = versionDir.getName();
                        for (String xmlPro : xmlPros) {
                            String[] tablePros = xmlPro.split("001");
                            String xmlMatch = tablePros[0];
                            if (versionName.matches(xmlMatch)) {
                                String dropTableKey = tablePros[1];
                                String createTableKey = tablePros[2];

                                System.out.println("versionName:" + versionName);
                                //File xmlFile = getXml(versionDir);
                                File xmlFile = versionDir;
                                //String outXmlFile=outXmlRegion+"/"+xmlFile.getName();

                                try {
                                    String xmlStr = ParseXml.readXml(xmlFile);
                                    String parseFields = ParseXml.pasrseXml(xmlStr);
                                    FileInputStream sqlFile = new FileInputStream(sqlPath);
                                    Properties sqlPros = new Properties();
                                    sqlPros.load(sqlFile);
                                    String dropTableValue = sqlPros.getProperty(dropTableKey);
                                    String createTableValue = sqlPros.getProperty(createTableKey);
                                    String[] createTable = createTableValue.split("001");
                                    String create_table = createTable[0] + parseFields + createTable[1];
                                    versionPro.setProperty(dropTableKey, dropTableValue);
                                    versionPro.setProperty(createTableKey, create_table);
                                    dealPro(outProFile, versionPro);
                                    //匹配到对应版本，即清除版本信息
                                    versionDirs.remove(xmlMatch);
                                    if (versionDirs.size() == 1) return 1;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    return 1;
                }
            });
            futrues.add(future);
        }
        return futrues;
    }

    private String getRegion(String regionPath) {
        int startIndex = regionPath.length() - 12;
        int endIndex = regionPath.length();
        return regionPath.substring(startIndex, endIndex);
    }

    private void dealPro(File outProFile, Properties versionPro) throws IOException {
        FileOutputStream fout = new FileOutputStream(outProFile);
        versionPro.store(fout, "generate properties");
        fout.close();
    }
}
