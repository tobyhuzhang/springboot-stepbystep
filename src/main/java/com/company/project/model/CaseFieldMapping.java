package com.company.project.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称: CaseFieldMapping
 * 类描述:  用戶信息字段映射类
 *
 * @author:
 * @since: 2021/5/31
 * @version: 1.0
 */

public class CaseFieldMapping {

    private static final Map<String, String> map = new HashMap() {
        {
            put("年龄", "age");
            put("性别", "sex");
            put("患者类型", "patientType");
            put("主诉", "chiefComplaint");
            put("现病史", "history");
            put("影像学检查", "examination");
            put("影像学检查结果", "examinationResult");
            put("其他检查", "examinationOther");
            put("治疗思路", "treatment");
            put("诊断结果", "diagnosis");
            put("第一次药物治疗情况", "drugTherapy");
            put("第一次随访","followUp");
            put("第二次随访","followUpTwo");
            put("转归（选填）","turnover");
        }
    };


    public static String getValue(String key) {
        return map.getOrDefault(key, null);
    }

    public static Map<String, String> getMap() {
        return map;
    }

}
