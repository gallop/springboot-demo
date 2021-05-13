package com.gallop.apidoc.vo;

import lombok.Data;

import java.util.Date;

/**
 * author gallop
 * date 2021-05-08 10:37
 * Description:
 * Modified By:
 */
@Data
public class UeRealTimeInfo {
    private String sn; //设备序列号
    private Integer status; //设备状态
    private Double soc; //电池电量百分比
    private Double voltage; //电池电压
    private Double current; //电池电流
    private Double temperature; // 电池温度
    private Date reportTime; //上报时间(yyyy-MM-dd HH:mm:ss)
}
