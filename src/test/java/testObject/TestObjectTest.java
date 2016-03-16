package testObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testobject.appium.junit.TestObjectTestResultWatcher;


public class TestObjectTest {

//	AndroidDriver<WebElement> driver;
	private AndroidDriver<WebElement> driver;
	Integer screenShotNumber = 0;
	
	// Sets the test name to the name of the test method
	@Rule
	public TestName testName = new TestName();
	
	// Takes care of sending the result of the tests ofver to TestObject
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	

	// This is the setup that will be run before the test
	@Before
	public void setUp() throws Exception {
		
		// 1. Appium이 테스트하는 환경 구성
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy Tab S 8.4"); // emulator, Amazon device farm
//		capabilities.setCapability("app", "Chrome"); // real device, Amazon device farm
//		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "12000");
//		// 스크린샷 생성할 때 필요함
//		capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		// demo page test
		capabilities.setCapability("testobject_api_key", "10354DCAB3A44673BC608257B754B60B"); // web demo
//		capabilities.setCapability("testobject_api_key", "A541BAEFA8B64108992CE559C216CD39"); // native app
//		capabilities.setCapability("testobject_api_key", "49DAFC3CDA2C4AFE98CBCD62A938D8FE"); // chrome apk
		capabilities.setCapability("testobject_app_id", "1");
		capabilities.setCapability("testobject_device", "LG_Nexus_4_E960_real");
//		capabilities.setCapability("testobject_device", "Asus_Google_Nexus_7_real");
//		capabilities.setCapability("testobject_device", "Asus_Google_Nexus_7_real");
		
		
		// web
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		
		
		// chrome apk
//		capabilities.setCapability("app", "Chrome");
//		capabilities.setCapability("appPackage", "com.android.chrome");
		
		
		// native app 
//		capabilities.setCapability("app", "TestForAppium");
//		capabilities.setCapability("appPackage", "com.example.testforappium");
//		capabilities.setCapability("appActivity", ".MainActivity");
		///////////////////////////////////////////////////////////
		
		capabilities.setCapability("testobject_appium_version", "1.3.6");
//		capabilities.setCapability("testobject_suite_name", "Default Appium Suite");
//		capabilities.setCapability("testobject_test_name", "Default Appium Test");
		
		System.out.println("before connect driver");
		driver = new AndroidDriver<WebElement>(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
//		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		System.out.println("after connect driver. success!!");
		
		// IMPORTANT!! We need to provide the Watcher with our initialized AppiumDriver
		resultWatcher.setAppiumDriver(driver);
		System.out.println("success Watcher!!");
	}

//	@After
//	public void tearDown() throws Exception {
//		if (driver != null) 
//		{
//			driver.quit();
//		}
//	}

	@Test
	public void testMethod() throws IOException {
		
		// mobile web test code \
		takeScreenshot();
//		driver.get("http://www.google.com");
		driver.get("http://211.63.24.57:7070/demo/jsp/talk/newTalk.jsp");
		pause(3000);
		takeScreenshot();
		
		new Select(driver.findElementById("category")).selectByValue("NODE0000000003");
        pause(2000);
        driver.findElementById("content").sendKeys("hello!!!!");
        pause(2000);
        driver.findElementByLinkText("채팅시작").click();
//      screenshot(driver, "d:/");
        pause(2000);
        driver.findElementById("layerConfirm").click();

        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("안녕하세요. 모바일 자동화 테스트 중입니다.");
        driver.findElementById("sendBtn").click();
        pause(2000);
        
        
        // 사진첨부 -> 디바이스마다 변경해 줄 필요성이 있음
        System.out.println(driver.getContextHandles());
        driver.findElementById("fileAttach").click();
        driver.context("NATIVE_APP");
        driver.findElementByName("카메라").click();
        System.out.println("카메라 클릭");
        pause(2000);
        
        // 카메라 셔터 클릭 -> 디바이스마다 변경해 줄 필요성이 있음
        pause(4000);
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
        System.out.println("카메라 셔터 클릭");
        pause(6000);
//        driver.findElementById("com.android.camera2:id/done_button").click();
        driver.findElementById("com.android.camera2:id/done_button").click(); // testobject nexus 4
        System.out.println("done 버튼 클릭");
        pause(4000);
        
        // 다시 웹으로 변경
        driver.context("WEBVIEW_1");
        
        System.out.println(driver.getContextHandles());
        
        // 이모티콘 전송
        driver.findElementById("btnEmoticon").click();
        pause(2000);
        
        List<WebElement> emoList = driver.findElementsByClassName("owl-item");
        
        emoList.get(3).click();
        driver.findElementById("sendBtn").click();
        pause(2000);
        
        // 이모티콘 다음 페이지로 넘기기
        driver.findElementsByClassName("owl-page").get(1).click();
        pause(2000);
        
        emoList.get(5).click();
        driver.findElementById("sendBtn").click();
        pause(2000);
        
        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("수고하세요");
        driver.findElementById("sendBtn").click();
        pause(2000);
        
//		// 상담종료
		driver.findElementById("btnMenu").click();
		takeScreenshot();
		pause(2000);
		driver.findElementByLinkText("상담종료").click();
		takeScreenshot();
		pause(2000);
		driver.findElementByLinkText("예").click();
		takeScreenshot();
		pause(3000);	
        
        
        // do more native testing if we want
        pause(2000);

		
		
//		System.out.println("google connected");
//		
//		driver.findElementById("lst-ib").sendKeys("spectra");
//		pause(2000);
//		takeScreenshot();
//		System.out.println("input spectra");
//		
//		driver.findElementByName("btnG").click();
//		pause(3000);
//		takeScreenshot();
//		System.out.println("click btnG");
		
		
		/////////////////////// apk test code ///////////////////////////
//    	WebElement el = driver.findElement(By.id("textView1"));
//    	// 아이디로 editText1 찾고
//    	assertEquals(el.getText(), "test Appium!!!!!");
//    	
//    	//WebElement edit = driver.findElement(By.id("editText1"));
//    	WebElement btn1 = driver.findElement(By.id("button1"));
//    	btn1.click();
//    	takeScreenshot();
//    	WebElement btn2 = driver.findElement(By.id("button2"));
//    	btn2.click();
//    	takeScreenshot();
//    	WebElement btn3 = driver.findElement(By.id("button3"));
//    	btn3.click();
//    	takeScreenshot();
//    	
//    	
//    	for(int i=0; i<10; i++) 
//    	{
//    		btn1.click();
//    		btn2.click();
//    		btn3.click();
//    		System.out.println("반복: " + (i+1));
//    		takeScreenshot();
//    		
//    	}
//		///////////////////////////////////////////////////
    	
    	System.out.println("end bye~!!");
	}
	
	public static void pause(int millisec) 
	{
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean takeScreenshot() throws IOException {
		
		// PC에 스크린샷 저장
		String contextName = driver.getContext();
		driver.context("NATIVE_APP"); // 네이티브 앱으로 전환
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
		
		// PC에 스크린샷 파일 저장 //
		File location = new File("screenshots");
		String screenShotName = location.getAbsolutePath() + File.separator + getTimestamp() + ".png";
		FileUtils.copyFile(screenShot, new File(screenShotName));
		// PC에 스크린샷 파일 저장 //
		
		driver.context(contextName); // 이전 컨텍스트로 복귀
		
		// Amazon Device Farm 스크린샷 저장
		screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", getTimestamp())));
	}

	private long getTimestamp() {
		return new Date().getTime();
	}
	
	


}
