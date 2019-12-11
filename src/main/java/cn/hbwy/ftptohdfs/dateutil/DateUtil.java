package cn.hbwy.ftptohdfs.dateutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 */
public class DateUtil {
    //得到现在日期格式为：yyyyMMddHHmm
    public static String getNow() {
        Date date = new Date();
        SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMddHHmm");
        String now = sdate.format(date.getTime());
        return now;
    }

    //得到分区时间减去hours的时间

    public static String regionMinus6(String region, int hours) throws Exception {

        SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMddHHmm");
        String regionMinus6 = "";
        //region:201810251015
        Date d = sdate.parse(region);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        //表述减去15m
        //表述减去6小时
        gc.add(10, hours);
        regionMinus6 = sdate.format(gc.getTime());
        return regionMinus6;
    }
}
