package com.company.project.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description case_export
 * @author 
 * @date 2021-12-03
 */
@Data
public class CaseExport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    /**
    * id
    */
    private Integer id;

    /**
    * 活动名称
    */
    private String activityTitle;

    /**
    * 年龄
    */
    private Integer age;

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
    * 医院
    */
    private String hospitalName;

    /**
    * 职称
    */
    private String positionName;

    /**
    * 性别
    */
    private Integer sex;

    /**
    * 患者类型：门诊，住院
    */
    private String patientType;

    /**
    * 主诉
    */
    private String chiefComplaint;

    /**
    * 现病史
    */
    private Integer history;

    /**
    * 影像学检查结果
    */
    private String examinationResult;

    /**
    * 目标科室
    */
    private String areaName;

    /**
    * 影像学检查
    */
    private String examination;

    /**
    * 提交时间
    */
    private LocalDateTime commitTime;

    /**
    * 其他检查
    */
    private String examinationOther;

    /**
    * 诊断结果
    */
    private String diagnosis;

    /**
    * 大区
    */
    private String regional;

    /**
    * 治疗思路
    */
    private String treatment;

    /**
    * 代表姓名
    */
    private String behalfName;

    /**
    * 代表手机号
    */
    private String behalfMobile;

    /**
    * 药物治疗情况
    */
    private String drugTherapy;

    /**
    * 第一次随访
    */
    private String followUp;

    /**
    * 第二次随访
    */
    private String followUpTwo;

    /**
    * 转归
    */
    private String turnover;

    public CaseExport() {}
}