package cn.hbwy.ftptohdfs.dealxml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class XmlMatch {
    public static void main(String[] args) throws IOException {
        List<String> finalPaths = XmlMatch.getMatchXml("G:\\Users\\lkz\\IdeaProjects\\ftptohdfsauto_v2\\src\\main\\java\\cn\\hbwy\\ftptohdfs\\hdfsdeal\\fianlpath.properties");
        for (String finalPath:finalPaths){
            System.out.println(finalPath);
        }

    }
    public static List<String> getMatchXml(String confFinalPath) throws IOException {
        Properties pro = new Properties();
        FileInputStream finalConfFileInputStream = new FileInputStream(confFinalPath);
        pro.load(finalConfFileInputStream);
        Set<Object> finalPathKeySets = pro.keySet();
       List<String> finalPathLists = new ArrayList<String>();
        for (Object finalPathKeySet:finalPathKeySets){
            String finalPathKey = finalPathKeySet.toString();
            String finalPath = pro.getProperty(finalPathKey);
            finalPathLists.add(finalPath);

        }
        return finalPathLists;
    }
}
