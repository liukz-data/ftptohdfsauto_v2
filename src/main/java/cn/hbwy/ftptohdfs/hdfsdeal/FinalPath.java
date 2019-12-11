package cn.hbwy.ftptohdfs.hdfsdeal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 得到结果文件的prperties文件内容
 */
public class FinalPath {

    public static List<String[]> getFinalPath(String confFinalPath) throws IOException {
        Properties pro = new Properties();
        FileInputStream finalConfFileInputStream = new FileInputStream(confFinalPath);
        pro.load(finalConfFileInputStream);
        Set<Object> finalPathKeySets = pro.keySet();
        List<String[]> finalPathLists = new ArrayList<String[]>();
        for (Object finalPathKeySet : finalPathKeySets) {
            String finalPathKey = finalPathKeySet.toString();
            String finalPath = pro.getProperty(finalPathKey);
            //System.out.println(finalPath);
            String[] finalPathStrs2 = finalPath.split("001");
            finalPathLists.add(finalPathStrs2);
        }
        return finalPathLists;
    }
}
