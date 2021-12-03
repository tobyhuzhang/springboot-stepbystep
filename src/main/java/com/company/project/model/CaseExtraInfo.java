/**
  * Copyright 2021 bejson.com 
  */
package com.company.project.model;
import java.util.List;

/**
 * Auto-generated: 2021-11-30 10:22:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CaseExtraInfo {

    private String code;
    private String isMust;
    private String name;
    private List<FieldList> fieldList;
    private boolean selected;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setIsMust(String isMust) {
         this.isMust = isMust;
     }
     public String getIsMust() {
         return isMust;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setFieldList(List<FieldList> fieldList) {
         this.fieldList = fieldList;
     }
     public List<FieldList> getFieldList() {
         return fieldList;
     }

    public void setSelected(boolean selected) {
         this.selected = selected;
     }
     public boolean getSelected() {
         return selected;
     }

}