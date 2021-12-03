package com.company.project.model.dto;

import com.company.project.model.CaseExport;
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
public class CaseExportDTO extends CaseExport implements Serializable {

    /**
     * 额外信息
     */
    private String extraInfo;
}