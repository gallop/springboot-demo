package com.gallop.apidoc.controller;

import com.gallop.apidoc.base.BaseResult;
import com.gallop.apidoc.base.BaseResultGenerator;
import com.gallop.apidoc.service.ServiceTest;
import com.gallop.apidoc.vo.ReqVo;
import com.gallop.apidoc.vo.UeRealTimeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * author gallop
 * date 2021-05-08 10:35
 * Description:
 * Modified By:
 */
@RestController
@RequestMapping("/apiDoc")
public class ApidocTestController {
    @Autowired
    private ServiceTest serviceTest;


    /**
     * @api {GET} /apiDoc/{sn}/rt-info getUeRealTimeInfo
     * @apiVersion 1.0.0
     * @apiGroup ApidocTestController
     * @apiName getUeRealTimeInfo
     * @apiParam (请求参数) {Number} userId
     * @apiParam (请求参数) {String} sn
     * @apiParamExample 请求参数示例
     * sn=FhYcee&userId=8822
     * @apiSuccess (响应结果) {String} sn
     * @apiSuccess (响应结果) {Number} status
     * @apiSuccess (响应结果) {Number} soc
     * @apiSuccess (响应结果) {Number} voltage
     * @apiSuccess (响应结果) {Number} current
     * @apiSuccess (响应结果) {Number} temperature
     * @apiSuccess (响应结果) {Number} reportTime
     * @apiSuccessExample 响应结果示例
     * {"current":5154.98,"soc":7805.08,"temperature":6548.74,"sn":"hdQBG","status":6719,"voltage":2734.92,"reportTime":3504994926819}
     */
    @RequestMapping(value = "/{sn}/rt-info", method = RequestMethod.GET)
    public UeRealTimeInfo getUeRealTimeInfo(@RequestHeader("header_login_user_key") long userId, @PathVariable("sn") String sn) {

        return new UeRealTimeInfo();
    }

    /**
     * @api {GET} /apiDoc/getTest getApiTest
     * @apiVersion 1.0.0
     * @apiGroup ApidocTestController
     * @apiName getApiTest
     * @apiParam (请求体) {String} requestBody
     * @apiParamExample 请求体示例
     * "WU"
     * @apiSuccess (响应结果) {String} response
     * @apiSuccessExample 响应结果示例
     * "2RaLz2"
     */
    @RequestMapping(value = "/getTest", method = RequestMethod.GET)
    public String getApiTest(@RequestBody String name) {

        return "";
    }

    /**
     * @api {POST} /apiDoc/queryHome queryHome
     * @apiVersion 1.0.0
     * @apiGroup ApidocTestController
     * @apiName queryHome
     * @apiDescription 查询首页数据
     * @apiParam (请求参数) {Object} bindingResult
     * @apiParamExample 请求参数示例
     * bindingResult=null
     * @apiParam (请求体) {String} dateStart
     * @apiParam (请求体) {String} dateEnd
     * @apiParam (请求体) {String} areaNumber
     * @apiParam (请求体) {String} rankCategory
     * @apiParam (请求体) {String} areaQueryType
     * @apiParamExample 请求体示例
     * {"rankCategory":"A","dateStart":"F2P","areaNumber":"6Ew","areaQueryType":"Of2","dateEnd":"mvPdC"}
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {Boolean} success
     * @apiSuccess (响应结果) {String} message
     * @apiSuccess (响应结果) {Object} entity
     * @apiSuccessExample 响应结果示例
     * {"code":1150,"success":false,"message":"Pu3","entity":{}}
     */
    @RequestMapping(value = "queryHome", method = RequestMethod.POST)
    public BaseResult queryHome(@RequestBody @Valid ReqVo req, BindingResult bindingResult) {
        //参数错误,返回结果
        if (bindingResult.hasErrors()) {
            return BaseResultGenerator.error(bindingResult.getFieldError().getDefaultMessage());
        }

        return BaseResultGenerator.success(serviceTest.queryHome(req));
    }

    /**
     * @api {POST} /apiDoc/queryData queryData 查询区域数据
     * @apiVersion 1.1.0
     * @apiGroup ApidocTestController
     * @apiName queryData
     * @apiParam (请求体) {String} dateStart 开始时间
     * @apiParam (请求体) {String} dateEnd 结束时间
     * @apiParam (请求体) {String} areaNumber 区域号码
     * @apiParam (请求体) {String} rankCategory 范围类型
     * @apiParam (请求体) {String} areaQueryType 区域查询类型
     * @apiParamExample 请求体示例
     * {"rankCategory":"vnBYr","dateStart":"YHc8C8","areaNumber":"l826xt","areaQueryType":"pKi0NpoT","dateEnd":"d"}
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {Boolean} success
     * @apiSuccess (响应结果) {String} message
     * @apiSuccess (响应结果) {Object} entity
     * @apiSuccess (响应结果) {String} sn 设备序列号
     * @apiSuccess (响应结果) {Number} status 设备状态
     * @apiSuccess (响应结果) {Number} soc 电池电量百分比
     * @apiSuccess (响应结果) {Number} voltage 电池电压
     * @apiSuccess (响应结果) {Number} current 电池电流
     * @apiSuccess (响应结果) {Number} temperature 电池温度
     * @apiSuccess (响应结果) {Number} reportTime 上报时间
     * @apiSuccessExample 响应结果示例
     * {"code":848,"success":false,"message":"CqpTk5P","entity":{"current": 5154.98,
     *     "soc": 7805.08,
     *     "temperature": 6548.74,
     *     "sn": "hdQBG",
     *     "status": 6719,
     *     "voltage": 2734.92,
     *     "reportTime": 3504994926819
     * }}
     */
    @RequestMapping(value = "queryData", method = RequestMethod.POST)
    public BaseResult queryData(@RequestBody @Valid ReqVo req){
        return BaseResultGenerator.success(serviceTest.queryData(req));
    }
}
