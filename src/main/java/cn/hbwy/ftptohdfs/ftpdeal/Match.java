package cn.hbwy.ftptohdfs.ftpdeal;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Match {

    private static String filter_nb = "tpd_eutrancell_q-ODM-.*_NB_V2.9.*.zip";
    private static String filter_start = "tpd_eutrancell_q-ODM.*.zip";
    /*private static String contains_v2_6 = "ODM-A.WL.PM.FILE_WL_4G_V2.6.0_ENODEB_PM";
    private static String contains_v2_7 = "ODM-A.WL.PM.FILE_WL_4G_V2.7.0_ENODEB";
    private static String match_v2_9 = "ODM-.*V2.9.*";
    private static String contains_fdd_v3_0 = "ODM-A.WL.PM.FILE_WL_LTE_COMMON_FDD_V3.0_XML_PM";
    private static String contains_tdd_v3_0 = "ODM-A.WL.PM.FILE_WL_LTE_COMMON_TDD_V3.0_XML_PM";
    private static String dealxml_v2_6 = "ODM-.*_V2.6.0.*";
    private static String dealxml_v2_7 = "ODM-.*_V2.7.0.*";
    private static String dealxml_v2_9 = "ODM-.*_V2.9.*";
    private static String dealxml_fdd_v3_0 = "ODM-.*_FDD_V3.0_.*";
    private static String dealxml_tdd_v3_0 = "ODM-.*_TDD_V3.0_.*";*/

    public static void initMatch(String matchPath) throws IOException {
        Properties pro = new Properties();
        FileInputStream fiPro = new FileInputStream(matchPath);
        pro.load(fiPro);
        filter_nb = pro.getProperty("filter_nb");
        filter_start = pro.getProperty("filter_start");
        /*contains_v2_6 = pro.getProperty("contains_v2_6");
        contains_v2_7 = pro.getProperty("contains_v2_7");
        match_v2_9 = pro.getProperty("match_v2_9");
        contains_fdd_v3_0 = pro.getProperty("contains_fdd_v3_0");
        contains_tdd_v3_0 = pro.getProperty("contains_tdd_v3_0");
        dealxml_v2_6 = pro.getProperty("dealxml_v2_6");
        dealxml_v2_7 = pro.getProperty("dealxml_v2_7");
        dealxml_v2_9 = pro.getProperty("dealxml_v2_9");
        dealxml_fdd_v3_0 = pro.getProperty("dealxml_fdd_v3_0");
        dealxml_tdd_v3_0 = pro.getProperty("dealxml_tdd_v3_0");*/
        fiPro.close();
    }

    public static String getFilter_nb() {
        return filter_nb;
    }

    public static String getFilter_start() {
        return filter_start;
    }

/*    public static String getContains_v2_6() {
        return contains_v2_6;
    }

    public static String getContains_v2_7() {
        return contains_v2_7;
    }

    public static String getMatch_v2_9() {
        return match_v2_9;
    }

    public static String getContains_fdd_v3_0() {
        return contains_fdd_v3_0;
    }

    public static String getContains_tdd_v3_0() {
        return contains_tdd_v3_0;
    }

    public static String getDealxml_v2_6() {
        return dealxml_v2_6;
    }

    public static String getDealxml_v2_7() {
        return dealxml_v2_7;
    }

    public static String getDealxml_v2_9() {
        return dealxml_v2_9;
    }

    public static String getDealxml_fdd_v3_0() {
        return dealxml_fdd_v3_0;
    }

    public static String getDealxml_tdd_v3_0() {
        return dealxml_tdd_v3_0;
    }*/

}
