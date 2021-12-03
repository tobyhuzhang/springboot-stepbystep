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
@Table(name = "t_blt_configuration_detail")
public class BltConfigurationDetail implements Serializable{
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
     * 病例字段code
     */
    private String configurationCode;

    /**
     * 病例字段主标题code
     */
    private String configurationName;
    /**
     * 字段类型 1文字、2数字、3日期、4下拉单选、5下拉多选、6邮箱、7视频、8图片
     */
    private String configurationType;

    /**
     * '是否内容自增 Y 是 N 否
     */
    private String contentFlag;

    /**
     * 是否必填 Y 是 N 否
     */
    private String requiredFlag;
    /**
     * 字是否基本字段
     */
    private String basicFlag;

    /**
     * 选项内容
     */
    private String jsonFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片数量
     */
    private Integer imgeNum;

    /**
     * 修改时间
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
    private Integer delFlag;

    /***
     * 单位
     */
    private String unit;

    /**
     * 创建人信息
     */
    @Transient
    private String userName;

    /**
     * 是否开启ocr Y 是 N 否
     */
    private String ocrFlag;

    /**
     * 上传图片/视频的说明文本
     */
    @Column(name = "`explain`")
    private String explain;
}
