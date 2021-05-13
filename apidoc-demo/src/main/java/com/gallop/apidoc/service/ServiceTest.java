package com.gallop.apidoc.service;

import com.gallop.apidoc.vo.ReqVo;
import com.gallop.apidoc.vo.UeRealTimeInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author gallop
 * date 2021-05-11 16:40
 * Description:
 * Modified By:
 */
@Service
public class ServiceTest {
    public List<UeRealTimeInfo> queryData(ReqVo req){
        List<UeRealTimeInfo> resultList = new ArrayList<>();
        resultList.add(new UeRealTimeInfo());
        resultList.add(new UeRealTimeInfo());
        resultList.add(new UeRealTimeInfo());

        return resultList;
    }
    public Map<String, Object> queryHome(ReqVo req) {
        Map<String,Object> result=new HashMap<>();
        result.put("totalScore",0);
        result.put("groupScore",buildGroupScore());

        return result;
    }
    private List<Map<String,Object>> buildGroupScore(){
        List<Map<String,Object>> list = new ArrayList<>(3);
        Map<String,Object> outputScoreMap = new HashMap<>(4);
        outputScoreMap.put("x","产出");
        outputScoreMap.put("y",41.135);
        outputScoreMap.put("max",50);
        outputScoreMap.put("group","得分");
        list.add(outputScoreMap);

        Map<String,Object> benefitScoreMap = new HashMap<>(4);
        benefitScoreMap.put("x","效益");
        benefitScoreMap.put("y",35.6);
        benefitScoreMap.put("max",40);
        benefitScoreMap.put("group","得分");
        list.add(benefitScoreMap);

        Map<String,Object> satisfactionScoreMap = new HashMap<>(4);
        satisfactionScoreMap.put("x","满意度");
        satisfactionScoreMap.put("y",8);
        satisfactionScoreMap.put("max",10);
        satisfactionScoreMap.put("group","得分");
        list.add(satisfactionScoreMap);

        return list;
    }
}
