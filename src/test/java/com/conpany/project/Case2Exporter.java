package com.conpany.project;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.Application;
import com.company.project.dao.CaseExportDao;
import com.company.project.dao.CaseExtraInfoDao;
import com.company.project.model.CaseExport;
import com.company.project.model.CaseExtraInfo;
import com.company.project.model.CaseFieldMapping;
import com.company.project.model.dto.CaseFieldDTO;
import com.company.project.util.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@Transactional
//@Rollback
public class Case2Exporter {

    @Autowired
    private CaseExportDao caseExportDao;

    @Autowired
    private CaseExtraInfoDao caseExtraInfoDao;

    @Test
    public void test() {

        Set<String> keys = null;
        // 创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        int roleNo = 0;
        int rowNo = 0;
        List<CaseExtraInfo> list = caseExtraInfoDao.selectAll();
        Map<String, String> map = CaseFieldMapping.getMap();
        for (CaseExtraInfo item : list) {
            List<CaseFieldDTO> caseFieldDTOList = new ArrayList<>();
            JSONObject jsonObject = JSONArray.parseArray(item.getExtraInfo()).getJSONObject(0);
            // 创建HSSFRow对象
            HSSFRow row = sheet.createRow(roleNo++);
            // 创建HSSFCell对象
            if (keys == null) {
                //标题
                keys = jsonObject.keySet();
                for (String s : keys) {
                    HSSFCell cell = row.createCell(rowNo++);
                    cell.setCellValue(s);
                }
                rowNo = 0;
                row = sheet.createRow(roleNo++);
            }

            for (String s : keys) {
                HSSFCell cell = row.createCell(rowNo++);
                cell.setCellValue(jsonObject.getString(s));
            }
            rowNo = 0;
            System.out.println(rowNo);

            JSONArray fieldList = jsonObject.getJSONArray("fieldList");
            for (int j = 0; j < fieldList.size(); j++) {
                JSONObject json = fieldList.getJSONObject(j);
                String fieldName = json.getString("fieldName");
                JSONArray otherValueArray = json.getJSONArray("otherValue");
                String otherText = json.getString("otherText");
                String textValue = json.getString("textValue");
                JSONArray array = json.getJSONArray("fieldValue");
                String fieldValue = "";
                if (ObjectUtil.isNotEmpty(array)) {
                    fieldValue += array.getString(0);
                }
                if (ObjectUtil.isNotEmpty(otherText)) {
                    fieldValue += otherText;
                }
                if (ObjectUtil.isNotEmpty(textValue)) {
                    fieldValue += textValue;
                }
                if (ObjectUtil.isNotEmpty(otherValueArray)) {
                    fieldValue += otherValueArray.getString(0);
                }

                if (StringUtils.isBlank(fieldValue)) {
                    JSONObject object = json.getJSONObject("followField");
                    if (ObjectUtil.isNotNull(object)) {
                        fieldValue += "随访时间：" + object.getString("followTime") + "周后";
                        fieldValue += "|检查结果：" + object.getString("textValue") + (ObjectUtil.isNotEmpty(object.getJSONArray("fieldValue")) ? object.getJSONArray("fieldValue").toJSONString() : "");
                        fieldValue += "|用药情况：同" + object.getString("followDrugs") + object.getString("otherDrugs");
                        fieldValue += "\r\n";
                    }
                }
                if (StringUtils.isBlank(fieldValue)) {
                    JSONArray otherValue = json.getJSONArray("otherValue");
                    JSONArray radioValue = json.getJSONArray("radioValue");
                    fieldValue += ObjectUtil.isNotEmpty(otherValue) ? otherValue.getString(0) : "";
                    fieldValue += ObjectUtil.isNotEmpty(radioValue) ? radioValue.getString(0) : "";

                }
                if (StringUtils.isBlank(fieldValue)) {
                    JSONArray drugList = json.getJSONArray("drugList");
                    if (ObjectUtil.isNotEmpty(drugList)) {
                        for (int k = 0; k < drugList.size(); k++) {
                            JSONObject object = drugList.getJSONObject(k);

                        }
                    }
                }
                for (String key : map.keySet()) {
                    if (fieldName.equals(key)) {
                        CaseFieldDTO caseField = new CaseFieldDTO(map.get(key), fieldValue);
                        caseFieldDTOList.add(caseField);
                        break;
                    }
                }
            }
            CaseExport instance = (CaseExport) ReflectionUtils.getInstance(CaseExport.class, caseFieldDTOList);
            BeanUtils.copyProperties(item, instance);
            caseExportDao.insertSelective(instance);
        }
    }
}



