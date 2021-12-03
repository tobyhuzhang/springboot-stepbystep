package com.company.project.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by TUF on 2020/7/23.
 */
@Data
@Accessors(chain = true)
@Table(name = "t_blt_configuration")
public class BltConfiguration implements Serializable{
    /**
     * 公司编号
     */
    private String companyCode;
    /**
     * 专区编号
     */
    private String areaCode;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 病例字段主标题code
     */
    private String code;
    /**
     * 病例字段主标题名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createPerson;
    /**
     * 修改人
     */
    private Long updatePerson;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标识【N:否,Y:是】
     */
    private String delFlag;

    /**
     * 是否基本字段 Y 是 N 否
     */
    private String basicFlag;

    /**
     * 创建人信息
     */
    @Transient
    private String userName;

}
