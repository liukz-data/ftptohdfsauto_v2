package cn.hbwy.ftptohdfs.hdfsdeal;

import java.io.File;
import java.util.ArrayList;

/**
 * 递归得到csv文件和xml文件
 */
public class FindCsv {
    private String zip_outPath = "/root/temp/extra_out/out";
    private static ArrayList<File> files_l = new ArrayList<File>();
    private ArrayList<File> files_xml = new ArrayList<File>();

    public FindCsv() {
    }

    public ArrayList getFiles(String zip_outPath) {
        findCsv(zip_outPath);
        return files_l;
    }

    public ArrayList getXmlFiles(String zip_outPath) {
        findXml(zip_outPath);
        return files_xml;
    }

    public void findCsv(String dir) {
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File file_r : files) {
            if (file_r.isDirectory()) {
                findCsv(file_r.getAbsolutePath());
            } else {
                String fileName = file_r.getName();
                //System.out.println(fileName);
                //will add regular
                if (fileName.endsWith(".csv")) {
                    files_l.add(file_r);
                }
            }
        }
    }

    public void findXml(String dir) {
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File file_r : files) {
            if (file_r.isDirectory()) {
                findXml(file_r.getAbsolutePath());
            } else {
                String fileName = file_r.getName();
                //System.out.println(fileName);
                //will add regular
                if (fileName.endsWith(".xml")) {
                    files_xml.add(file_r);
                }
            }
        }
    }

    public static ArrayList<File> getListFiles() {
        return FindCsv.files_l;
    }

    public ArrayList<File> getFiles_xml() {
        return files_xml;
    }
}
