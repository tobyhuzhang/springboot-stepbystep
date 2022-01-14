package com.conpany.project;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.company.project.Application;
import com.company.project.dao.CaseLLSHDao;
import com.company.project.dao.CaseLLSHExportDao;
import com.company.project.model.CaseExportDO;
import com.company.project.model.CaseLLSHDO;
import com.company.project.util.FileTools;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 类名称: BaseTest
 * 类描述:
 *
 * @author:
 * @since: 2018/7/23
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {


    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    private CaseLLSHDao caseLLSHDao;

    @Autowired
    private CaseLLSHExportDao caseLLSHExportDao;

    public void snapshotBySelenium() {
        // 根据系统来添加不同的驱动路径
        String os = System.getProperty("os.name");
        if (StringUtils.containsIgnoreCase(os, "Windows 10")) {
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        } else {
            // 只考虑Linux环境,需要下载对应版本的驱动然后放置在/usr/bin目录下
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }
        ChromeDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
            options.addArguments("--user-data-dir=C:/Users/yidaohu/AppData/Local/Google/Chrome/User Data");
            options.addArguments("--profile-directory=Default");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            execute(driver);
        } catch (Exception e) {
            log.error("execute失败", e);
        } finally {
            driver.quit();
        }
    }

    public void snapshotBySelenium(Boolean islabour, String url) {
        // 根据系统来添加不同的驱动路径
        String os = System.getProperty("os.name");
        if (StringUtils.containsIgnoreCase(os, "Windows 10")) {
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        } else {
            // 只考虑Linux环境,需要下载对应版本的驱动然后放置在/usr/bin目录下
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }
        ChromeDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
            options.addArguments("--user-data-dir=C:/Users/yidaohu/AppData/Local/Google/Chrome/User Data");
            options.addArguments("--profile-directory=Default");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get(url);
            String jsHeight = "return document.body.clientHeight";
            long height = (long) driver.executeScript(jsHeight);
            int k = 1;
            int size = 540;
            // 设置窗口尺寸，注意宽高之间使用逗号而不是x

            while (k * size < height) {
                String jsMove = String.format("window.scrollTo(0,%s)", k * 600);
                driver.executeScript(jsMove);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                height = (long) driver.executeScript(jsHeight);
                k += 1;
            }

            long maxWidth = (long) driver.executeScript(
                    "return Math.max(document .body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth);");
            long maxHeight = (long) driver.executeScript(
                    "return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");

            Dimension targetSize = new Dimension((int) maxWidth, (int) maxHeight);
            driver.manage().window().setSize(targetSize);
            File img = driver.getScreenshotAs(OutputType.FILE);

            if (islabour) {
                FileUtil.copyFile(img, new File("D:/path/labour/" + img.getName()));
            } else {
                FileUtil.copyFile(img, new File("D:/path/case/" + img.getName()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }


    private void execute(ChromeDriver driver) {
        List<CaseLLSHDO> list = caseLLSHDao.selectAll();
        String url = "https://yaotongyun.com/cloud/#/caseAduit/caseDetail?code={0}&stageCode={1}&status={2}&recommendFlag={3}&stageName={4}&activityCode={5}&caseTitle={6}";
        String labourUrl = "https://yaotongyun.com/cloud/#/bulkContract/gxmyDetails?id={0}&contractNo={1}";
        Map<String, List<CaseLLSHDO>> listMap = list.stream().collect(Collectors.groupingBy(CaseLLSHDO::getAreaCode));
        for (Map.Entry<String, List<CaseLLSHDO>> entry : listMap.entrySet()) {
            AtomicInteger count = new AtomicInteger();
            List<CaseExportDO> exportList = new ArrayList<>();
            List<CaseLLSHDO> areaCodeList = entry.getValue();
            if (ObjectUtil.isEmpty(areaCodeList)) {
                continue;
            }
            log.info("科室："+entry.getKey()+ "，执行前条数：" + areaCodeList.size());
            areaCodeList.forEach(p -> {
                CaseExportDO caseExportDO = new CaseExportDO();
                BeanUtils.copyProperties(p, caseExportDO);
                String executeUrl = MessageFormat.format(url, p.getCode(), p.getStageCode(), p.getStatus(), p.getRecommendFlag(), p.getStageName(), p.getActivityCode(), p.getActivityTitle());
                getDriver(driver, executeUrl);
                File img = driver.getScreenshotAs(OutputType.FILE);
                count.getAndIncrement();
                FileUtil.copy(img, new File("D:/path/case/" + p.getAreaName() + "/" + p.getRegional() + "/" + p.getUserName() + "$" + p.getTitle() + "$" + p.getActivityTitle() + "$" + p.getCode() + ".png"), true);
                String upload = FileTools.localFileUpload(img.getAbsolutePath());
                caseExportDO.setUrl(upload);
//                if (StringUtils.isNotBlank(p.getContractId())) {
//                    String executeLabourUrl = MessageFormat.format(labourUrl, p.getContractId(), p.getContractNo());
//                    getDriver(driver, executeLabourUrl);
//                    File labourImg = driver.getScreenshotAs(OutputType.FILE);
//                    try {
//                        FileUtils.copyFile(labourImg, new File("D:/path/labour/" + p.getAreaName() + "/" + p.getUserName() + "$" + p.getTitle() + "$" + p.getActivityTitle() + count + ".png"));
//                    } catch (IOException e) {
//                        log.error("版权截图拷贝失败", e);
//                    }
//                    String labourUpload = FileTools.localFileUpload(labourImg.getAbsolutePath());
//                    caseExportDO.setLabourUrl(labourUpload);
//                }
                exportList.add(caseExportDO);
                if (count.get() % 50 == 0) {
                    log.info("已执行条数：" + count);
                    caseLLSHExportDao.insertList(exportList);
                    exportList.clear();
                }
            });
            caseLLSHExportDao.insertList(exportList);

        }
    }

    private void getDriver(ChromeDriver driver, String url) {
        driver.get(url);
        String jsHeight = "return document.body.clientHeight";
        long height = (long) driver.executeScript(jsHeight);
        int k = 1;
        int size = 540;
        // 设置窗口尺寸，注意宽高之间使用逗号而不是x

        while (k * size < height) {
            String jsMove = String.format("window.scrollTo(0,%s)", k * 600);
            driver.executeScript(jsMove);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            height = (long) driver.executeScript(jsHeight);
            k += 1;
        }

        long maxWidth = (long) driver.executeScript(
                "return Math.max(document .body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth);");
        long maxHeight = (long) driver.executeScript(
                "return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");

        Dimension targetSize = new Dimension((int) maxWidth, (int) maxHeight);
        driver.manage().window().setSize(targetSize);
    }

    @Test
    public void test() {
        snapshotBySelenium();
        //snapshotBySelenium(false,"https://yaotongyun.com/cloud/#/ca/caseDetail?code=210923092738200054&stageCode=2021091016063600002&status=5&recommendFlag=1&stageName=%E7%AC%AC%E4%B8%80%E9%98%B6%E6%AE%B5&activityCode=2021091016063600002&topFlag=0&caseTitle=%E6%AF%94%E4%BE%8B%E8%BE%83%E5%AE%8C%E6%95%B4%EF%BC%8C%E9%95%BF%E6%9C%9F%E6%B2%BB%E7%96%97%EF%BC%8C%E7%97%8A%E6%84%88");
    }
}
