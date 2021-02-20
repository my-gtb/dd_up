package com.bs.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {

    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 精确到秒的时间戳
     * @return
     */
    public static String timeStamp2Date(Long timeStamp) {
        if(timeStamp == null || timeStamp == 0){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeStamp * 1000));
    }

    public static Long getTodayTimeStamp(){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date.getTime()/1000;
    }
}
