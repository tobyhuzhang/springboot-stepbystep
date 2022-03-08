package com.conpany.project;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.dao.CaseExtraInfoDao;
import com.company.project.model.CaseExtraInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 单元测试继承该类即可
 */
@Slf4j
public class CaseDetailExporter extends Tester {


    @Autowired
    private CaseExtraInfoDao caseExtraInfoDao;

    @Test
    public void test() throws IOException {

        Set<String> keys = null;
        // 创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        int rowNo = 0;
        int cellNo = 0;
        int cellHeadNo = 0;
        List<CaseExtraInfo> list = caseExtraInfoDao.selectAll();
        list = list.subList(0, 7372);
        // list = list.subList(7372, list.size());
        HSSFRow rowHead = sheet.createRow(0);
        HSSFCell cellHead;
        HSSFCell cell;
        for (CaseExtraInfo item : list) {
            // 创建HSSFRow对象
            HSSFRow row = sheet.createRow(++rowNo);
            if (rowNo % 1000 == 0) {
                log.info("rowNo第" + rowNo + "行");
            }
            JSONArray jsonArray = JSONArray.parseArray(item.getExtraInfo());
            if (ObjectUtil.isEmpty(jsonArray)) {
                continue;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray fieldList = jsonArray.getJSONObject(i).getJSONArray("fieldList");


                String fieldValue = "";
                for (int j = 0; j < fieldList.size(); j++) {
                    JSONObject json = fieldList.getJSONObject(j);
                    String fieldName = json.getString("fieldName");
                    JSONArray otherValueArray = json.getJSONArray("otherValue");
                    String otherText = json.getString("otherText");
                    String textValue = json.getString("textValue");
                    JSONArray fieldArray = json.getJSONArray("fieldValue");
                    JSONArray radioArray = json.getJSONArray("radioValue");
                    boolean flag1 = "第一次药物治疗情况".equals(fieldName);
                    boolean flag2 = "第一次随访".equals(fieldName);
                    boolean flag3 = "第二次随访".equals(fieldName);
                    boolean flag4 = "转归（选填）".equals(fieldName);
                    if (flag1) {
                        continue;
                    }

                    //标题
                    if (rowNo == 1 && !flag2 && !flag3) {
                        cellHead = rowHead.createCell(cellHeadNo++);
                        cellHead.setCellValue(fieldName);
                    }

                    if (!flag2 && !flag3 & !flag4) {
                        cell = row.createCell(cellNo++);
                        if (ObjectUtil.isNotEmpty(fieldArray)) {
                            fieldValue = fieldArray.toJSONString();
                        }
                        fieldValue += (ObjectUtil.isNull(textValue) ? "" : textValue);
                        cell.setCellValue(fieldValue);
                        continue;

                    }
                    if (flag4) {
                        cell = row.createCell(cellNo++);
                        if (ObjectUtil.isNotEmpty(radioArray)) {
                            fieldValue = radioArray.toJSONString();
                            ;
                        }
                        fieldValue += ObjectUtil.isNull(textValue) ? "" : textValue;
                        cell.setCellValue(fieldValue);
                        continue;
                    }


                    JSONObject object = json.getJSONObject("followField");
                    if (ObjectUtil.isNotEmpty(object) && flag2) {
                        if (rowNo == 1) {
                            fieldName = "随访时间1";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                            fieldName = "检查结果1";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                            fieldName = "用药情况1";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                        }
                        // cellHead= rowHead.createCell(cellHeadNo++);
                        // cellHead.setCellValue(fieldValue);
                        fieldValue = ObjectUtil.isEmpty(object.getString("followTime")) ? "" : object.getString("followTime") + "周后";
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);
                        fieldValue = object.getString("textValue");
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);
                        fieldValue = "1".equals(object.getString("followDrugs")) ? "同1" : object.getString("otherDrugs");
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);

                    }
                    if (ObjectUtil.isNotEmpty(object) && flag3) {
                        if (rowNo == 1) {
                            fieldName = "随访时间2";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                            fieldName = "检查结果2";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                            fieldName = "用药情况2";
                            cellHead = rowHead.createCell(cellHeadNo++);
                            cellHead.setCellValue(fieldName);
                        }
                        fieldValue = ObjectUtil.isEmpty(object.getString("followTime")) ? "" : object.getString("followTime") + "周后";
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);
                        fieldValue = object.getString("textValue");
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);
                        fieldValue = "1".equals(object.getString("followDrugs")) ? "同1" : object.getString("otherDrugs");
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(fieldValue);

                    }
                }
            }
            for (int n = 0; n < jsonArray.size(); n++) {
                String name = jsonArray.getJSONObject(n).getString("name");
                if ("治疗经过".equals(name)) {
                    JSONArray drugList = jsonArray.getJSONObject(n).getJSONArray("fieldList").getJSONObject(1).getJSONArray("drugList");
                    String fieldValue = "";
                    for (int k = 0; k < drugList.size(); k++) {
                        JSONObject json = drugList.getJSONObject(k);
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(json.getString("drugName"));
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(json.getString("drugType").equals("1") ? "口服" : "注射");
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(json.getString("drugDose") + "mg");
                        cell = row.createCell(cellNo++);

                        switch (json.getString("usage")) {
                            case "1":
                                fieldValue = "每日一次（qd）";
                                break;
                            case "2":
                                fieldValue = "一日两次（bid）";
                                break;
                            case "3":
                                fieldValue = "一日三次（tid）";
                                break;
                            case "4":
                                fieldValue = "一日四次（qid）";
                                break;
                            case "5":
                                fieldValue = json.getString("otherUsage");
                                break;
                            default:
                        }
                        cell.setCellValue(fieldValue);
                        cell = row.createCell(cellNo++);
                        cell.setCellValue(json.getString("time") + "天");
                    }
                }
            }
            cellNo = 0;

            // 输出Excel文件
            FileOutputStream output = new FileOutputStream("C:\\Users\\yidaohu\\Desktop\\caseDetails11.xls");
            wb.write(output);
            wb.close();
            output.flush();
            output.close();
        }
    }
}



