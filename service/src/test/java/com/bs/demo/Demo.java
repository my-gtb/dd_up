package com.bs.demo;

import com.bs.common.utils.DateUtil;
import com.bs.service.entity.QuestionGroupType;
import com.bs.service.entity.WeiXinData;
import com.bs.service.entity.constants.QuestionGroupEnum;
import com.bs.service.entity.wx.QuestionHistoryForm;
import com.bs.service.service.ICustomerQuestionLogService;
import com.bs.service.service.IQuestionGroupTypeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Demo {

    @Autowired
    private IQuestionGroupTypeService questionGroupTypeService;

    @Autowired
    private ICustomerQuestionLogService questionLogService;

    @Test
    public void dateDemo(){
        long time = new Date().getTime();
        int time1 = (int) Math.floor(time / 1000);
        long l = System.currentTimeMillis();
        System.out.println(time1);
        System.out.println(time);
        System.out.println((int) Math.floor(System.currentTimeMillis()/1000));

    }

    @Test
    public void enumDemo(){
        List<QuestionGroupType> typeList = questionGroupTypeService.getList();

        System.out.println(typeList);
    }

    @Test
    public void WeiXinTest(){
        WeiXinData weiXinData = new WeiXinData();
        System.out.println(weiXinData.getAppId());
        System.out.println(weiXinData.getAppSecret());
    }

    @Test
    public void demo1(){
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void getQuestionHistories(){
        String code = "1613007851039-1";
        Map<String,Object> questionHistories = questionLogService.getQuestionHistories(code);
    }

    @Test
    public void testGetTime(){
//        try {
//            Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            String yesterday = simpleDateFormat.format(today);//获取昨天日期
//            System.out.println(yesterday);
//            Date date = simpleDateFormat.parse(yesterday);
//            long unixTimestamp = today.getTime()/1000;
//            System.out.println(unixTimestamp);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,0,0,0);
//        long tt = calendar.getTime().getTime()/1000;
//        System.out.println(tt);

//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.add(Calendar.DAY_OF_MONTH, -1);
//
//        Date yesterday = c.getTime();//这是昨天

        long yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24).getTime();
        long today = new Date().getTime();

        System.out.println(yesterday > today);
    }

    @Test
    public void Demo2(){
//        String s = DateUtil.timeStamp2Date(1613470914l);
//        System.out.println(s);
        Date d = new Date(1613470914l * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(d);
        System.out.println(format);
    }

    @Test
    public void Demo3() throws ParseException {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        long time = date.getTime()/1000;
        long yTime = time - 24 * 60 * 60;

        System.out.println(time);
        System.out.println(yTime);
    }
}
