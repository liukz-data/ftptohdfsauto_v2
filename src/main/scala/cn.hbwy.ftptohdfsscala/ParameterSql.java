package cn.hbwy.ftptohdfsscala;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * sql配置文件的orm
 */
public class ParameterSql {
    private static String d1_V2_6;
    private static String t1_V2_6;
    private static String d2_V2_7;
    private static String t2_V2_7;
    private static String d3_V2_9;
    private static String t3_V2_9;
    private static String d4_fdd_V3_0;
    private static String t4_fdd_V3_0;
    private static String d5_tdd_V3_0;
    private static String t5_tdd_V3_0;
    private static String l1_V2_6;
    private static String l1_V2_7;
    private static String l1_V2_9;
    private static String l1_fdd_V3_0;
    private static String l1_tdd_V3_0;
    private static String select_V2_6;
    private static String select_V2_7;
    private static String select_V2_9;
    private static String select_fdd_V3_0;
    private static String select_tdd_V3_0;
    private static String insert_wide;
    private static String select_to_middle;
    private static String insert_ods_intermediate;
    private static String selete_from_intermediate;
    private static String insert_dm_fifteen_result;
    private static String wide_fields;

    public static void initSql(String sqlConfPath) throws IOException {
        FileInputStream fileInputSql = new FileInputStream(sqlConfPath);
        Properties proInputSql = new Properties();
        proInputSql.load(fileInputSql);
        d1_V2_6 = proInputSql.getProperty("d1_V2_6");
        t1_V2_6 = proInputSql.getProperty("t1_V2_6");
        d2_V2_7 = proInputSql.getProperty("d2_V2_7");
        t2_V2_7 = proInputSql.getProperty("t2_V2_7");
        d3_V2_9 = proInputSql.getProperty("d3_V2_9");
        t3_V2_9 = proInputSql.getProperty("t3_V2_9");
        d4_fdd_V3_0 = proInputSql.getProperty("d4_fdd_V3_0");
        t4_fdd_V3_0 = proInputSql.getProperty("t4_fdd_V3_0");
        d5_tdd_V3_0 = proInputSql.getProperty("d5_tdd_V3_0");
        t5_tdd_V3_0 = proInputSql.getProperty("t5_tdd_V3_0");
        l1_V2_6 = proInputSql.getProperty("l1_V2_6");
        l1_V2_7 = proInputSql.getProperty("l1_V2_7");
        l1_V2_9 = proInputSql.getProperty("l1_V2_9");
        l1_fdd_V3_0 = proInputSql.getProperty("l1_fdd_V3_0");
        l1_tdd_V3_0 = proInputSql.getProperty("l1_tdd_V3_0");
        select_V2_6 = proInputSql.getProperty("select_V2_6");
        select_V2_7 = proInputSql.getProperty("select_V2_7");
        select_V2_9 = proInputSql.getProperty("select_V2_9");
        select_fdd_V3_0 = proInputSql.getProperty("select_fdd_V3_0");
        select_tdd_V3_0 = proInputSql.getProperty("select_tdd_V3_0");
        insert_wide = proInputSql.getProperty("insert_wide");
        select_to_middle = proInputSql.getProperty("select_to_middle");
        insert_ods_intermediate = proInputSql.getProperty("insert_ods_intermediate");
        selete_from_intermediate = proInputSql.getProperty("selete_from_intermediate");
        insert_dm_fifteen_result = proInputSql.getProperty("insert_dm_fifteen_result");
        wide_fields = proInputSql.getProperty("wide_fields");
    }

    public static String getD1_V2_6() {
        return d1_V2_6;
    }

    public static String getT1_V2_6() {
        return t1_V2_6;
    }

    public static String getD2_V2_7() {
        return d2_V2_7;
    }

    public static String getT2_V2_7() {
        return t2_V2_7;
    }

    public static String getD3_V2_9() {
        return d3_V2_9;
    }

    public static String getT3_V2_9() {
        return t3_V2_9;
    }

    public static String getD4_fdd_V3_0() {
        return d4_fdd_V3_0;
    }

    public static String getT4_fdd_V3_0() {
        return t4_fdd_V3_0;
    }

    public static String getD5_tdd_V3_0() {
        return d5_tdd_V3_0;
    }

    public static String getT5_tdd_V3_0() {
        return t5_tdd_V3_0;
    }

    public static String getL1_V2_6() {
        return l1_V2_6;
    }

    public static String getL1_V2_7() {
        return l1_V2_7;
    }

    public static String getL1_V2_9() {
        return l1_V2_9;
    }

    public static String getL1_fdd_V3_0() {
        return l1_fdd_V3_0;
    }

    public static String getL1_tdd_V3_0() {
        return l1_tdd_V3_0;
    }

    public static String getSelect_V2_6() {
        return select_V2_6;
    }

    public static String getSelect_V2_7() {
        return select_V2_7;
    }

    public static String getSelect_V2_9() {
        return select_V2_9;
    }

    public static String getSelect_fdd_V3_0() {
        return select_fdd_V3_0;
    }

    public static String getSelect_tdd_V3_0() {
        return select_tdd_V3_0;
    }

    public static String getInsert_wide() {
        return insert_wide;
    }

    public static String getSelect_to_middle() {
        return select_to_middle;
    }

    public static String getInsert_ods_intermediate() {
        return insert_ods_intermediate;
    }

    public static String getSelete_from_intermediate() {
        return selete_from_intermediate;
    }

    public static String getInsert_dm_fifteen_result() {
        return insert_dm_fifteen_result;
    }

    public static String getWide_fields() {
        return wide_fields;
    }
}
