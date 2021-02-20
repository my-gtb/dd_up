package com.bs.demo;

import com.bs.service.DdUpApplication;
import com.bs.service.entity.CustomerSign;
import com.bs.service.entity.CustomerWrong;
import com.bs.service.entity.PointLog;
import com.bs.service.entity.Question;
import com.bs.service.entity.wx.QuestionHistoryForm;
import com.bs.service.entity.wx.QuestionHistoryVo;
import com.bs.service.entity.wx.WxQuestionForm;
import com.bs.service.mapper.QuestionMapper;
import com.bs.service.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DdUpApplication.class)
public class SqlTest {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuestionGroupService questionGroupService;

    @Autowired
    private ICustomerQuestionLogService questionLogService;

    @Autowired
    private ICustomerQuestionLogService customerQuestionLogService;

    @Autowired
    private ICustomerSignService signService;

    @Autowired
    private IPointLogService pointLogService;

    @Autowired
    private ICustomerWrongService customerWrongService;

    @Test
    public void TestDemo(){
        List<WxQuestionForm> questionList = questionService.getQuestionList(12);
        for (WxQuestionForm form : questionList) {
            System.out.println(form);
        }

    }

    @Test
    public void TestDemo1(){
        List<QuestionHistoryVo> questionHistory = customerQuestionLogService.getQuestionHistory("1613005504674-1");
        for (QuestionHistoryVo form : questionHistory) {
            System.out.println(form);
        }
        System.out.println("-------------------------------------");
        System.out.println(questionHistory);
    }

    @Test
    public void getQuestionHistories(){
        String code = "1613525107-2";
        Map<String,Object> map = questionLogService.getQuestionHistories(code);
        System.out.println(map);
    }

    @Test
    public void getQuestionId(){
        Question questionId = questionService.getRandomQuestion(5);
        System.out.println(questionId);
    }

    @Test
    public void getQuestionFormById(){
        WxQuestionForm questionId = questionService.getQuestionFormById(30);
        System.out.println(questionId);
    }

    @Test
    public void customerWrongService(){
        CustomerWrong wrong = new CustomerWrong();
        wrong.setCustomerId(2);
        wrong.setIsWrong(true);
        Map<String, Object> questionCount = customerWrongService.getCustomerQuestionCount(wrong);
        System.out.println(questionCount);
        System.out.println(questionCount.get("countMap"));
    }

    @Test
    public void handleCustomerSign(){
        CustomerSign sign = new CustomerSign();
        sign.setCustomerId(2);
        sign.setSignTime(1613577600l);
        Map<String, Object> map = signService.handleCustomerSign(sign);
        System.out.println(map);
    }

    @Test
    public void testPointLog(){
        PointLog pointLog = new PointLog();
        pointLog.setCustomerId(2);
        pointLog.setTypeId(1);
        pointLog.setValue(-3);
        pointLog.setContent("222222222222222222");
        boolean save = pointLogService.save(pointLog);
        System.out.println(save);
    }
}
