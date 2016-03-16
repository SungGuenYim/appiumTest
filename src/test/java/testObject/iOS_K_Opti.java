package testObject;

import static org.junit.Assert.assertTrue;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Keys;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

/**
 * 안드로이드 웹 에뮬레이터 테스트
 * 
 * @author shlim
 *
 */
public class iOS_K_Opti {
	
	private IOSDriver driver;
	Integer screenShotNumber = 0;
	int failCount = 0;
	
	// Sets the test name to the name of the test method
	@Rule
	public TestName testName = new TestName();
	
	// Takes care of sending the result of the tests offer to TestObject
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

	@Before
	public void setUp() throws Exception 
	{		
		// iOS setting
		DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
        dc.setCapability("testobject_api_key", "14FD45CE1D75492891545A58B1D0339A");
        dc.setCapability("testobject_app_id", "1");
        dc.setCapability("testobject_device", "iPhone_5_16GB_real");
        dc.setCapability("browserName", "safari");
        dc.setCapability("autoWebView", true);
        dc.setCapability("nativeWebTap", true);
        dc.setCapability("autoAcceptAlerts", true);
        dc.setCapability("safariAllowPopups", true);
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
        
		dc.setCapability("testobject_appium_version", "1.4.16"); // for iOS 1.3.6 / 1.4.16
		
		System.out.println("before connect driver");
		pause(2000);
		driver = new IOSDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), dc);
		pause(8000);
		System.out.println("after connect driver. success!!");
		
		// IMPORTANT!! We need to provide the Watcher with our initialized AppiumDriver
		resultWatcher.setAppiumDriver(driver);
		System.out.println("success Watcher!!");
	}

	@After
	public void tearDown() throws Exception 
	{
		driver.quit();
	}

	@Test
	public void testMethod() throws Exception 
	{	
		// URL 호출
		driver.get("http://111.90.160.180:17070/x/sph.jsp");
		pause(3000);
		
		// 도메인 들어가기
		driver.findElementByLinkText("1. domain : eonet").click();
		pause(2500);
		
		// 배너 노출될 때 까지 기다리기
		System.out.println("배너 노출? " + driver.getPageSource().contains("_t_banner"));
		while (!driver.getPageSource().contains("_t_banner"))
		{
			pause(1500);
			System.out.println("배너 아직 안뜸");
			// 15초를 기다려도 뜨지 않으면 오류로 간주
			failCount++;
			assertTrue(failCount < 10);
		}
		System.out.println("배너 노출");
		failCount = 0;
				
		// 배너가 채팅창으로 바뀔 때 까지 기다리기
		System.out.println("채팅창 노출? " + driver.getPageSource().contains("_t_mini_window"));
		while (!driver.getPageSource().contains("_t_mini_window"))
		{
			pause(1500);
			System.out.println("채팅창 아직 안뜸");
			// 15초를 기다려도 뜨지 않으면 오류로 간주
			failCount++;
			assertTrue(failCount < 10);
		}
		System.out.println("채팅창 노출");
		failCount = 0;
		// 배너클릭
		
		///////////////////////////////////////////////////////////////
		// iframe 안 (톡 안)으로 변경
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		System.out.println("frame switch");
		pause(5000);
	
		driver.findElementById("message").sendKeys("모바일테스트자동화중입니다.");
		driver.findElementById("sendBtn").click();
		pause(5000);
		
		// 상담사가 상담을 받기 전, 몇명의 고객이 기다립니다. 창이 있으면
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
			System.out.println("_t_close click!!");
		}
		pause(3000);
		
		/////////////////////////////
		
		// 최소화 
		driver.findElementByClassName("icon_btn").findElement(By.className("minimize")).click();
		System.out.println("minimize click");
		pause(3000);
				
		// 원래 frame으로 돌아오기 (배너 누르기 위해서)
		driver.switchTo().defaultContent();
		System.out.println("switch to mainFrame");
		pause(3000);
		
		// 다시 배너 클릭해서 채팅창으로
		driver.findElementByClassName("_t_banner_img").click();
		System.out.println("banner click");
		pause(3000);
		
		// 다시 채팅창 안으로
		System.out.println("switch frame to mini window");
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		pause(2000);
		
//		Point popupBtn = driver.findElementByClassName("icon_btn").findElement(By.className("popup")).getLocation();
//		System.out.println("popup X: " + popupBtn.getX()); // 249
//		System.out.println("popup Y: " + popupBtn.getY()); // 13
		
//		driver.tap(1, popupBtn.getX(), popupBtn.getY(), 50);
//		driver.tap(1, driver.findElementByClassName("icon_btn").findElement(By.className("popup")), 50);
//		tapOn(driver.findElementByClassName("icon_btn").findElement(By.className("popup")));
//		tabEl(driver.findElementByClassName("icon_btn").findElement(By.className("popup")));
//		System.out.println("popup click!");
//		pause(10000);
		
		
		// 이모티콘 전송1 (전송버튼) -> ios에서 이모티콘 선택이 안됨. -> 좌표로 찍어보기
		driver.findElementById("btnEmoticon").click();
		System.out.println("btnEmotion click");
		pause(6000);

		// 이모티콘 좌표로 클릭
		
		List<WebElement> emoList = driver.findElementsByClassName("owl-item");
		pause(2000);
//		driver.tap(1, emoList.get(1), 20);
		
		Point p = ((Locatable)emoList.get(1)).getCoordinates().onPage();
		Point p2 = ((Locatable)emoList.get(2)).getCoordinates().onPage();
//		driver.tap(1, p.getX(), p.getY(), 1);
		System.out.println("X: " + p.getX()); // 63
		System.out.println("Y: " + p.getY()); // 324
		
		System.out.println("X2: " + p2.getX()); // 63
		System.out.println("Y2: " + p2.getY()); // 324
		
		driver.context("NATIVE_APP");
		System.out.println("NATIVE_APP으로 전환");
		pause(5000);
		driver.tap(1, 63, 360, 50);
		System.out.println("이모티콘 선택");
		pause(3000);
		
//		driver.switchTo().defaultContent();
		driver.context("WEBVIEW_1");
		System.out.println("WEBVIEW context로 전환");
		pause(5000);
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		System.out.println("frame 안으로 들어오기");
		pause(5000);
		
		
		driver.findElementById("sendBtn").click();
		pause(2000);
		System.out.println("이모티콘 좌표로 눌러 전송");
		
		
//		emoList.get(1).click();
//		System.out.println("emoticon click");
//		pause(2000);
		
		
		driver.findElementById("message").sendKeys("이모티콘 전송!!");
		pause(2000);
		driver.findElementById("sendBtn").click();
		System.out.println("send btn click");
		pause(2000);
		System.out.println("emoticon send 1");
		
		driver.findElementById("message").sendKeys("after minimize chat");
		driver.findElementById("sendBtn").click();
		pause(2000);
		
//		// 이모티콘 전송2 (이모티콘 두번 눌러서) -> ios에서 이모티콘 선택이 안됨
//		driver.findElementById("message").clear();
//		driver.findElementById("btnEmoticon").click();
//		pause(2000);
//		emoList = driver.findElementsByClassName("owl-item");
//		emoList.get(2).click();
//		pause(2000);
//		emoList.get(2).click();
//		driver.findElementById("sendBtn").click();
//		pause(2000);
//		System.out.println("emoticon send 2");
		
		///////////////// 팝업창 챗팅 시작 ////////////////////
//		System.out.println("before click popup btn");
//		pause(2000);
		
		// ios에서 팝업창이 안뜸... dc.setCapability("safariAllowPopups", true); 안먹음 ㅠㅠ
		driver.findElementByClassName("icon_btn").findElement(By.className("popup")).click();
		pause(10000);
		System.out.println("popup btn click");
		
		// window 전환
		String oriWindow = driver.getWindowHandle();
//		driver.switchTo().window("1");
		pause(3000);
		System.out.println("auto switch window?");
		
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
		}
				
		driver.findElementById("message").sendKeys("모바일테스트자동화중입니다.");
		driver.findElementById("sendBtn").click();
		pause(3000);
		System.out.println("message send");
//		
//		// 이모티콘 전송1 - 팝업창 (전송버튼) - ios에서 이모티콘 클릭이 안됨
//		driver.findElementById("btnEmoticon").click();
//		pause(3000);
//		
////		emoList = driver.findElementsByClassName("owl-item");
////		emoList.get(1).click();
//		pause(2000);
//		driver.findElementById("sendBtn").click();
//		pause(2000);
//		System.out.println("emoticon send 1");
		
//		// 이모티콘 전송2 - 팝업창 (이모티콘 두번 눌러서) - ios에서 이모티콘 클릭이 안됨
//		driver.findElementById("message").clear();
//		driver.findElementById("btnEmoticon").click();
//		pause(2000);
//		emoList.get(2).click();
//		pause(2000);
//		emoList.get(2).click();
////		driver.findElementById("sendBtn").click();
//		pause(2000);
//		System.out.println("emoticon send 2");
		
		// 다시 작은창으로
		driver.findElementByClassName("icon_btn").findElement(By.className("_t_close")).click();
		pause(3000);
		System.out.println("close popup");
		
		///////////////// 팝업창 챗팅 끝 ////////////////////
		
		
		// 다시 원래 윈도우로 돌아오기
//		driver.switchTo().window(oriWindow);
		driver.switchTo().defaultContent();
		pause(3000);
		
		// iframe 안 (톡 안)으로 변경
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		
		// 상담 종료하기
		driver.findElementById("btnChend").click();
		pause(2000);
		System.out.println("end counselor");
		
		driver.findElementByClassName("btn_end").click();
		pause(2000);
		System.out.println("btn_end click");
		
		// 설문이 있는 경우
		if(driver.findElementById("surveyLayer").isDisplayed() == true)
		{			
			driver.findElementById("surveyLayer").findElement(By.className("star_off")).click();
			pause(1500);
			System.out.println("별점주기");
			
			driver.findElementById("surveyMsg").findElement(By.id("option01")).sendKeys("상담 감사합니다.");
			pause(2000);
			driver.findElementById("surveyLayer").findElement(By.className("btn_end")).click();
			pause(2000);
			
			// 마지막 팝업창 닫기
//			driver.context("NATIVE_APP");
//			pause(5000);
//			System.out.println("context switch to NATIVE_APP");
//			
////			driver.switchTo().alert().accept();
////			driver.findElementByName("OK").click();
//			System.out.println("system alert OK");
//			pause(3000);
			
			System.out.println("설문 후 테스트 종료");

		}
		// 설문이 없는 경우 (상담사가 상담을 받지 않으면 채팅 종료 후 설문이 없다.)
		else
		{
			System.out.println("설문 없이 종료");
		}

		
	}

	/**
	 * 전달되는 밀리세컨트 만큼 멈추기
	 * 
	 * @param millisec
	 */
	public static void pause(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testScreenshot() 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
//		String screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BASE64);
		
		File file = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
	}
	
	public void tapOn(WebElement element)
	{
		driver.context("NATIVE_APP");
//		new TouchAction((MobileDriver)driver).tap(element).perform();
		TouchAction action = new TouchAction((MobileDriver) driver);
		action.press(element).perform();
		driver.switchTo().defaultContent();
	}
	
	public void tabEl (WebElement element)
	{
		driver.context("NATIVE_APP");
		pause(2000);
		driver.tap(1, element, 10);
		pause(2000);
		driver.switchTo().defaultContent();
	}

}

