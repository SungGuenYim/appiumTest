package testObject;

import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testobject.appium.junit.TestObjectTestResultWatcher;


public class iOSTestWeb {

	private IOSDriver<WebElement> driver;
	Integer screenShotNumber = 0;
	
	// Sets the test name to the name of the test method
	@Rule
	public TestName testName = new TestName();
	
	// Takes care of sending the result of the tests offer to TestObject
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	

	// This is the setup that will be run before the test
	@Before
	public void setUp() throws Exception {
		
		// iOS setting
		DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
        dc.setCapability("testobject_api_key", "713731982DB94032BE3C4A72C77BD5E9");
        dc.setCapability("testobject_app_id", "1");
        dc.setCapability("testobject_device", "iPhone_5_16GB_real");
        dc.setCapability("language", "korean");
        dc.setCapability("browserName", "safari");
        dc.setCapability("autoWebView", true);
        dc.setCapability("nativeWebTap", true);
        
		dc.setCapability("testobject_appium_version", "1.4.16"); // for iOS 1.3.6 / 1.4.16
//		dc.setCapability("testobject_suite_name", "Default Appium Suite");
//		dc.setCapability("testobject_test_name", "Default Appium Test");
		
		System.out.println("before connect driver");
		pause(2000);
		driver = new IOSDriver<WebElement>(new URL("https://app.testobject.com:443/api/appium/wd/hub"), dc);
		pause(8000);
		System.out.println("after connect driver. success!!");
		
		// IMPORTANT!! We need to provide the Watcher with our initialized AppiumDriver
		resultWatcher.setAppiumDriver(driver);
		System.out.println("success Watcher!!");
	}

	@After
	public void tearDown() throws Exception {
		if (driver != null) 
		{
			driver.quit();
		}
	}

	@Test
	public void testMethod() throws IOException {
		
		// mobile web test code \
//		takeScreenshot();
		driver.get("http://211.63.24.57:7070/demo/jsp/talk/newTalk.jsp");
		pause(12000);
//		takeScreenshot();
		System.out.println("demo connected");
		System.out.println("getCapabilities" + driver.getCapabilities());
		System.out.println("------------------------------------------");
		
//		System.out.println("pageSource(): " + driver.getPageSource());
		
//        driver.findElement(By.id("category")).click();
		//takeScreenshot();
//        System.out.println("context: " + driver.getContextHandles());
//        driver.context("NATIVE_APP");
//        driver.tap(1, 150, 600, 100);
//        //takeScreenshot();
//        pause(2000);
//        driver.tap(1, 350, 450, 100);
//        //takeScreenshot();
//        driver.context("WEBVIEW_1");
//        pause(2000);
		
		driver.findElement(By.id("content")).sendKeys("this is test!!");
		pause(1000);
		testScreenshot();
		
		
		new Select(driver.findElementById("category")).selectByValue("NODE0000000003");
        pause(2000);        
        testScreenshot();
        
        driver.findElementByLinkText("채팅시작").click();
        pause(2000);
        testScreenshot();
        
        //takeScreenshot();
        driver.findElementById("layerConfirm").click();
        pause(1000);
        
        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("안녕하세요. 모바일자동화 테스트 중입니다.");
        driver.findElementById("sendBtn").click();
        //takeScreenshot();
        pause(1500);
        
        
        System.out.println("before fileAttach click");
        driver.findElementById("fileAttach").click();
        pause(2000);
        System.out.println("after fileAttach click");
        
        System.out.println("context: " + driver.getContextHandles());
        System.out.println("cur context: " + driver.getContext());
        
                
        // 이모티콘 전송
        driver.findElementById("btnEmoticon").click();
        pause(2000);
        
        List<WebElement> tempEmo = driver.findElementsByClassName("owl-item");        
        System.out.println("emoticon count: " + tempEmo.size());
        
        tempEmo.get(0).click();        
        pause(2000);
        driver.findElement(By.id("sendBtn")).click();
        pause(2000);
        testScreenshot();
        
        tempEmo.get(2).click();
        pause(2000);
        driver.findElement(By.id("sendBtn")).click();
        pause(2000);
        testScreenshot();
        
        // 상담 종료
        pause(1000);
        driver.findElement(By.id("btnMenu")).click();
        pause(1000);
        //takeScreenshot();
        driver.findElement(By.linkText("상담종료")).click();
        pause(2000);
        //takeScreenshot();
        driver.findElement(By.linkText("예")).click();
        pause(1000);
        //takeScreenshot();
        
    	
    	System.out.println("finish!! bye~!!");
	}
	
	public static void pause(int millisec) 
	{
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void takeScreenshot() throws IOException 
	{		
        String contextName = driver.getContext();
        System.out.println("takescreenShot ori context: " + contextName);

        driver.context("NATIVE_APP");
        System.out.println("takescreenShot changed context: " + driver.getContext());
        pause(2000);
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File location = new File("screenshots");
        String screenShotName = location.getAbsolutePath() + File.separator + getTimestamp() + ".png";
        FileUtils.copyFile(screenShot, new File(screenShotName));
        
        driver.context(contextName);
        pause(2000);
	}
	
	public void testScreenshot() 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
//		String screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BASE64);
		
		File file = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
	}

	private long getTimestamp() {
		return new Date().getTime();
	}
	
	


}
