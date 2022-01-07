package com.conpany.project;

import com.company.project.algorithm.Phash;
import com.company.project.algorithm.data.CImage;
import org.junit.Test;

/**
 * @author huyan
 * @date 2022/1/4
 */
public class PhashTest {

    // 项目根目录路径
    public static final String path = System.getProperty("user.dir");

    @Test
    public void test() {
        Phash phash = new Phash();
        CImage imA = new CImage(path + "\\temp\\source2.jpg");
        CImage imB = new CImage(path + "\\temp\\example2.jpg");
        double v = phash._ph_compare_images(imA, imB);
        System.out.println(v);
    }
}
