package com.company.project.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description
 * @author
 * @date 2021-06-23
 */
@Data
@Table(name="t_llsh_case")
public class CaseLLSHDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    /**
    * id
    */
    private Integer id;

    /**
    * activity_title
    */
    private String activityTitle;

    /**
     * activity_code
     */
    private String activityCode;

    /**
    * code
    */
    private String code;

    /**
    * user_name
    */
    private String userName;

    /**
    * mobile
    */
    private String mobile;

    /**
    * hospital_name
    */
    private String hospitalName;

    /**
    * position_name
    */
    private String positionName;

    /**
    * department_name
    */
    private String departmentName;

    /**
     * stage_code
     */
    private String stageCode;

    /**
     * stage_name
     */
    private String stageName;

    /**
     * status
     */
    private Integer status;

    /**
     * recommend_flag
     */
    private String recommendFlag;

    private String areaCode;

    private String areaName;

    private String title;

    private String commitTime;

    private String contractId;

    private String contractNo;

    private String regional;

    private String district;

    private String behalfName;

    private String behalfMobile;

    public CaseLLSHDO() {}
}