package com.company.project.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @description case_export
 * @author 
 * @date 2021-12-03
 */
@Table(name = "t_case_export")
@AllArgsConstructor
@NoArgsConstructor
public class CaseExport extends CaseExtraInfo {

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String sex;

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
    private String history;

    /**
     * 影像学检查结果
     */
    private String examinationResult;

    /**
     * 影像学检查
     */
    private String examination;

    /**
     * 其他检查
     */
    private String examinationOther;

    /**
     * 诊断结果
     */
    private String diagnosis;

    /**
     * 治疗思路
     */
    private String treatment;

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

    
    public String getSex() {
        return sex;
    }

    
    public String getPatientType() {
        return patientType;
    }

    
    public String getChiefComplaint() {
        return chiefComplaint;
    }

    
    public String getHistory() {
        return history;
    }

    
    public String getExaminationResult() {
        return examinationResult;
    }


    public String getExamination() {
        return examination;
    }

    
    public String getExaminationOther() {
        return examinationOther;
    }

    
    public String getDiagnosis() {
        return diagnosis;
    }

    
    public String getTreatment() {
        return treatment;
    }

    
    public String getDrugTherapy() {
        return drugTherapy;
    }

    
    public String getFollowUp() {
        return followUp;
    }

    
    public String getFollowUpTwo() {
        return followUpTwo;
    }

    
    public String getTurnover() {
        return turnover;
    }

    public String getAge() {
        return age;
    }
}