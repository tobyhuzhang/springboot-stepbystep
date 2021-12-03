package com.company.project.dao;

import com.company.project.core.MyMapper;
import com.company.project.model.CaseExport;
import com.company.project.model.dto.CaseExportDTO;

import java.util.List;

/**
 * @description case_export
 * @author 
 * @date 2021-12-03
 */
public interface CaseExportDao extends MyMapper<CaseExport> {

    List<CaseExportDTO> listTemp();

}