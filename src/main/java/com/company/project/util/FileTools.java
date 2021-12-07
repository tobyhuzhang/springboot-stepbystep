package com.company.project.util;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.util.ArrayList;


public class FileTools {

    public static final int IMG = 1;

    private static final Logger logger = LoggerFactory.getLogger(FileTools.class);

    /**
     * 获取项目根目录
     *
     * @return 根目录
     */
    public static String getWebRootPath() {
        return System.getProperty("web.root");
    }

    /**
     * 获取头像目录，若不存在则直接创建一个
     *
     * @Param userID 用户ID
     * @return
     */
    public static String getPortraitPath(int userID) {
        String realPath = getWebRootPath() + "img/portrait/" + userID + "/";
        File file = new File(realPath);
        //判断文件夹是否存在，不存在则创建一个
        if (!file.exists() || !file.isDirectory()) {
            if (!file.mkdirs()) {
                logger.info("创建文件夹失败！");
            }
        }
        return realPath;
    }

    /**
     * 重命名头像文件
     *
     * @Param fileName 文件名
     * @return
     */
    public static String getPortraitFileName(String fileName) {
        // 获取文件后缀
        String suffix = getSuffix(fileName);
        return "portrait" + suffix;
    }

    /**
     * 判断文件后缀是否符合要求
     *
     * @Param fileName    文件名
     * @Param allowSuffix 允许的后缀集合
     * @return
     * @throws Exception
     */
    public static boolean checkSuffix(String fileName, String[] allowSuffix) throws Exception {
        String fileExtension = getSuffix(fileName);
        boolean flag = false;
        for (String extension : allowSuffix) {
            if (fileExtension.equals(extension)) {
                flag = true;
            }
        }
        return flag;
    }


    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 将文件地址转成链接地址
     *
     * @Param filePath 文件路径
     * @Param fileType 文件类型
     * @return
     */
    public static String filePathToSRC(String filePath, int fileType) {
        String href = "";
        if (null != filePath && !"".equals(filePath)) {
            switch (fileType) {
                case IMG:
                    if (filePath.contains("/img/")) {
                        int index = filePath.indexOf("/img/");
                        href = filePath.substring(index);
                    }
                    return href;
                default:
                    href = "";
            }
        }
        return href;
    }

    /**
     * 获取指定文件或文件路径下的所有文件清单
     *
     * @Param fileOrPath 文件或文件路径
     * @return
     */
    public static ArrayList<File> getListFiles(Object fileOrPath) {
        File directory;
        if (fileOrPath instanceof File) {
            directory = (File) fileOrPath;
        } else {
            directory = new File(fileOrPath.toString());
        }

        ArrayList<File> files = new ArrayList<File>();

        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            if (null != fileArr && fileArr.length != 0) {
                for (File fileOne : fileArr) {
                    files.addAll(getListFiles(fileOne));
                }
            }
        }

        return files;
    }


    /**
     * 截图工具，根据截取的比例进行缩放裁剪
     *
     * @Param path        图片路径
     * @Param zoomX       缩放后的X坐标
     * @Param zoomY       缩放后的Y坐标
     * @Param zoomW       缩放后的截取宽度
     * @Param zoomH       缩放后的截取高度
     * @Param scaleWidth  缩放后图片的宽度
     * @Param scaleHeight 缩放后的图片高度
     * @return 是否成功
     * @throws Exception 任何异常均抛出
     */
    public static boolean imgCut(String path, double zoomX, double zoomY, double zoomW,
                                 double zoomH, double scaleWidth, double scaleHeight) throws Exception {
        Image img;
        ImageFilter cropFilter;
        BufferedImage bi = ImageIO.read(new File(path));
        int fileWidth = bi.getWidth();
        int fileHeight = bi.getHeight();
        double scale = (double) fileWidth / (double) scaleWidth;

        double realX = zoomX * scale;
        double realY = zoomY * scale;
        double realW = zoomW * scale;
        double realH = zoomH * scale;

        if (fileWidth >= realW && fileHeight >= realH) {
            Image image = bi.getScaledInstance(fileWidth, fileHeight, Image.SCALE_DEFAULT);
            cropFilter = new CropImageFilter((int) realX, (int) realY, (int) realW, (int) realH);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage bufferedImage = new BufferedImage((int) realW, (int) realH, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            //输出文件
            return ImageIO.write(bufferedImage, "JPEG", new File(path));
        } else {
            return true;
        }
    }

    /**
     * 本地文件上传至七牛云
     * @Param filePath
     * @return
     */
    public static String localFileUpload(String filePath){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "DATfjiENBPBKhjGDiW-dXvfS2ZiyDQqp_vb6u7Uz";
        String secretKey = "LozIGkyNln11sajrLNghZS9sT8lU1OeptXeSUrnR";
        String bucket = "chat-img";
        //添加html过滤
        StringMap imgPutPolicy = new StringMap();
        imgPutPolicy.put("mimeLimit", "image/*;application/zip;application/x-ole-storage;application/vnd.ms-powerpoint;image/*;video/*;audio/*;application/vnd.openxmlformats-officedocument.presentationml.presentation");

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, 60000,imgPutPolicy, true);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "http://studioimage.yxj.org.cn/"+putRet.hash;
        } catch (QiniuException ex) {
            logger.info("文件上传七牛云失败：{}",ex);
        }
        return null;
    }
}