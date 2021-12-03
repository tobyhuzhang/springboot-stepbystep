package com.conpany.project;


import com.company.project.Application;
import com.company.project.dao.CaseExportDao;
import com.company.project.model.dto.CaseExportDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class CaseExporter {

    private CaseExportDao caseExportDao;

    @Test
    public void test(){
        List<CaseExportDTO> list=caseExportDao.listTemp();
        list.forEach(p->
                System.out.println(p.getCode())
        );
    }
}



