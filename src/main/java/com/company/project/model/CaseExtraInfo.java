package com.company.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description case_export
 * @author 
 * @date 2021-12-03
 */
@Data
@Accessors(chain = true)
@Table(name = "t_case_extra_info")
@AllArgsConstructor
public class CaseExtraInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    /**
    * id
    */
    @Column(insertable = false, name = "id")
    private Integer id;

    /**
    * 活动名称
    */
    private String activityTitle;


    /**
    * 病例编号
    */
    private String code;

    /**
    * 姓名
    */
    private String userName;

    /**
    * 手机号
    */
    private String mobile;

    /**
     * 产品
     */
    private String product;

    /**
    * 医院
    */
    private String hospitalName;

    /**
    * 职称
    */
    private String positionName;

    /**
    * 目标科室
    */
    private String areaName;

    /**
    * 提交时间
    */
    private LocalDateTime commitTime;

    /**
    * 大区
    */
    private String regional;

    /**
    * 代表姓名
    */
    private String behalfName;

    /**
    * 代表手机号
    */
    private String behalfMobile;


    /**
     * 额外信息
     */
    private String extraInfo;

    public CaseExtraInfo() {}

}