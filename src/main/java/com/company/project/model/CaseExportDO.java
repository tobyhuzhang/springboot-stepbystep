package com.company.project.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description case_export
 * @author zhengkai.blog.csdn.net
 * @date 2021-06-24
 */
@Data
@Table(name = "t_llsh_case_export")
public class CaseExportDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    /**
    * id
    */
    private Integer id;

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
    * area_name
    */
    private String areaName;

    /**
    * url
    */
    private String url;

    /**
     * labour_url
     */
    private String labourUrl;

    /**
     * code
     */
    private String code;

    private String title;

    private String commitTime;

    private String regional;

    private String district;

    private String behalfName;

    private String behalfMobile;

    public CaseExportDO() {}
}