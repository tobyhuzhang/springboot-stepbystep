package com.company.project.util;

import com.company.project.model.Smss;
import com.company.project.model.dto.CaseFieldDTO;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 反射的通用工具类 
 *  
 * @author lz 
 *  
 */  
public class ReflectionUtils {  
      
    /** 
     * 用于对类的字段赋值，无视private,project修饰符,无视set/get方法 
     * @param c 要反射的类 
     * @param args 类的字段名和值 每个字段名和值用英文逗号隔开 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Object getInstance(Class c, String... args) {  
        try {  
            Object object = Class.forName(c.getName()).newInstance();  
            Class<?> obj = object.getClass();  
            Field[] fields = obj.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {  
                fields[i].setAccessible(true);  
                for (int j = 0; j < args.length; j++) {  
                    String str = args[j];  
                    String strs[] = str.split(",");  
                    if (strs[0].equals(fields[i].getName())) {  
                        fields[i].set(object, strs[1]);  
                        break;  
                    }  
                }  
            }  
            return object;  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }

    @SuppressWarnings("unchecked")
    public static Object getInstance(Class c, List<CaseFieldDTO> list) {
        try {
            Object object = Class.forName(c.getName()).newInstance();
            Class<?> obj = object.getClass();
            Field[] fields = obj.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                for (CaseFieldDTO caseField: list){
                    if (caseField.getFieldName().equals(fields[i].getName())) {
                        fields[i].set(object, caseField.getFieldValue());
                        break;
                    }
                }
            }
            return object;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {  
        Object object = getInstance(Smss.class, "destID,08340001", "mobile,13408340000", "content,ReYo测试数据。");
                Smss sms = (Smss) object;  
                System.out.println("短信内容：" + sms.getContent());  
                System.out.println("手机号码：" + sms.getMobile());  
                System.out.println("尾号：" + sms.getDestID());  
    }  
}  