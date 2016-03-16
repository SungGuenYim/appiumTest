package testObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testobject.appium.common.TestObjectCapabilities;
import static org.testobject.appium.common.TestObjectCapabilities.TESTOBJECT_TEST_REPORT_ID;

/**
 * 안드로이드 웹 에뮬레이터 테스트
 * 
 * @author sklim
 *
 */
public class JpnTestPage {
	AndroidDriver<WebElement> driver;
	static final String DEVICE_NAME = "LG_Nexus_4_E960_real";
	static final String TESTOBJECT_API_KEY = "14FD45CE1D75492891545A58B1D0339A";
	String testReportId;
	int screenShotNumber = 0;
	int failCount = 0;
	
	// 메시지 설정
	// 접수 메시지: リアルタイムチャットを受付けました。 실시간 채팅을 접수했습니다.
	static final String appiumURL = "https://app.testobject.com:443/api/appium/wd/hub";
	// 인사말. domain: 1. domain : eonet, agent4 counselor / customer 구분해서 메시지 갯수 개면 되겠지
	static final String MSG_INTRO = "info:歓迎します。";
	static final String MSG_GREETING = "お待たせ致しました。お問合せ有難うございます。nakaoがお伺い致します。"; 
	static final String MSG_RECEIPT = "リアルタイムチャットを受付けました。"; // 접수메시지
	static final String MSG_START_CHAT = "Mobile automation test ..."; // 상담 신청 메시지
	static final String MSG_SEND_MINI_WINDOW = "(mini_window) This is mobile automation test"; // 고객이 전송할 메시지
	static final String MSG_SEND_POPUP = "(popup) This is mobile automation test2";
	static final String WAIT_MSG = "3分間ご返信がございませんので、円滑な応対のために5分間待機させて頂き、リアルタイムチャットを自動的に終了させる事をご了承お願い致します。";
	static final String WAIT_MSG_END = "先にご案内の通り、5分以上ご返信が御座いませんので、リアルタイムチャットを終了させて頂きます。引き続きご質問が御座いましたら、改めて再度お問い合せ下さい。";
//	static final String MSG_END = "リアルタイムチャットを終了させて頂きます。ご利用有難う御座いました。"; // 상담원이 종료했을 때 메시지
	static final String MSG_END = "リアルタイムチャットを終了致しました。ご利用有難う御座いました。"; // 고객이 종료했을 때 메시지
	
	// Trigger 설정 - 배너
	static final boolean isTriggerOn = true;
	
	
	@Before
	public void setUp() throws Exception {
		
		// testobject test code	
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String apiKey = System.getenv(TESTOBJECT_API_KEY);
//		int appId = Integer.parseInt(System.getenv("1"));
		String deviceId = System.getenv(DEVICE_NAME);
		
//		capabilities.setCapability("testobject_api_key", "14FD45CE1D75492891545A58B1D0339A");
//		capabilities.setCapability("testobject_app_id", "1");
//		capabilities.setCapability("testobject_device", DEVICE_NAME);
		
//		capabilities.setCapability(TestObjectCapabilities.TESTOBJECT_API_KEY, apiKey);
		capabilities.setCapability(TestObjectCapabilities.TESTOBJECT_API_KEY, TESTOBJECT_API_KEY);
//		capabilities.setCapability(TestObjectCapabilities.TESTOBJECT_TEST_REPORT_ID, testReportId);
		capabilities.setCapability(TestObjectCapabilities.TESTOBJECT_APP_ID, "1");
		capabilities.setCapability(TestObjectCapabilities.TESTOBJECT_DEVICE, DEVICE_NAME);
		capabilities.setCapability("testobject_appium_version", "1.4.16");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, false);
		
		driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
		
		////////////////////////////////////////////////////////////

		// 1. local Appium이 테스트하는 환경 구성
//		DesiredCapabilities cap = new DesiredCapabilities();
//		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
//		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy Tab S 8.4"); // emulator, Amazon device farm
//		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome"); // real device, Amazon device farm
//		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "12000");
//		capabilities.setCapability("unicodeKeyboard", "true");
//		// 스크린샷 생성할 때 필요함
//		cap.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
//		
//		// 2. 드라이버 생성, 앱 구동
//		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
	}

	@After
	public void tearDown() throws Exception {
		// driver.close();
		driver.quit();
	}


	@Test
	public void testMethod() throws Exception 
	{
		// URL 호출
		driver.get("http://111.90.160.180:17070/x/sph.jsp");
		System.out.println("K-Opti site connect");
		pause(3000);
		takeScreenshot();
		
		driver.findElementByLinkText("1. domain : eonet").click();
		System.out.println("1. domain : eonet click");
		pause(2500);
		takeScreenshot();
		
		driver.findElementByLinkText("info1 (O)").click();
		System.out.println("info1 (0) click");
		pause(2500);
		
		// 배너가 나타났는지 확인
		while (!containPageSource("_t_banner"))
		{
			pause(1000);
			System.out.println("banner not yet appeared");
			// 10초를 기다려도 뜨지 않으면 오류로 간주
			failCount++;
			assertTrue(failCount < 10);
		}
		System.out.println("*** banner appeared!! ***");
		takeScreenshot();
		failCount = 0;
				
		// 배너가 채팅창으로 변했는지 확인
		if (isTriggerOn)
		{
			while (!containPageSource("_t_mini_window"))
			{
				pause(1000);
				System.out.println("mini window chat not yet appeared");
				// 10초를 기다려도 뜨지 않으면 오류로 간주
				failCount++;
				assertTrue(failCount < 10);
			}
			System.out.println("*** mini window chat appeared!! ***");
			takeScreenshot();
			failCount = 0;			
		}
		
		// 배너가 자동으로 채팅창으로 변하지 않는다면
		else
		{
			driver.findElementByClassName("_t_banner").click();
			System.out.println("*** _t_banner click ***");
			pause(2000);
		}

		///////////////////////////////////////////////////////////////
		// iframe 안 (톡 안)으로 변경
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		System.out.println("frame switch to mini window");
		pause(2000);
		
		// 인사말 확인
		assertEquals(true, containPageSource(MSG_INTRO));
		System.out.println("*** MSG_GREETING OK ***");
		pause(1000);
		
		driver.findElementById("message").sendKeys(MSG_START_CHAT);
		driver.findElementById("sendBtn").click();
		pause(1000);
		
		assertEquals(true, containPageSource(MSG_START_CHAT));
		pause(1000);
		
		System.out.println("*** MSG_START_CHAT send ***");
		takeScreenshot();
		
		System.out.println("*** chat start ***");
		
		
		// hide keyboard; some device cannot click _t_close because of software keyboard
		String preContext = driver.getContext();
		driver.context("NATIVE_APP");
		pause(1000);
		driver.pressKeyCode(AndroidKeyCode.BACK); // for hide keyboard
		System.out.println("hide keyboard");
		pause(1000);
		
		driver.context(preContext);
		pause(1000);
		
		////////////////////////////////////////////
		// 각 도메인 및 카테고리의 하위페이지로 이동하여 배너를 확인 //
		////////////////////////////////////////////
		
		// 상담사가 상담을 받기 전, 몇명의 고객이 기다립니다. 창이 있으면
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
			System.out.println("_t_close click!!");
		}
		
		pause(3000);
		
		
		// 채팅 접수메시지 확인
		assertEquals(true, containPageSource(MSG_RECEIPT));
		System.out.println("*** MSG_RECEIPT OK ***");
		
		driver.findElementById("message").sendKeys(MSG_SEND_MINI_WINDOW);
		driver.findElementById("sendBtn").click();
		pause(1000);
		
		assertEquals(true, containPageSource(MSG_SEND_MINI_WINDOW));
		System.out.println("*** MSG_SEND_MINI_WINDOW send ***");
		
		// hide keyboard; some device cannot click _t_close because of software keyboard
		pause(1000);
		preContext = driver.getContext();
		driver.context("NATIVE_APP");
		pause(1000);
		driver.pressKeyCode(AndroidKeyCode.BACK); // for hide keyboard
		pause(1000);
		
		driver.context(preContext);
		pause(1000);
		
		
		/////////////////////////////		
		// 최소화
		driver.findElementByClassName("icon_btn").findElement(By.className("minimize")).click();		
		System.out.println("minimize click");
		pause(2000);
		 
		// 채팅창 밖으로 나가서
		driver.switchTo().parentFrame();

		// 다시 창
		driver.findElementByClassName("_t_banner_img").click();
		System.out.println("banner click");
		pause(3000);
		
		System.out.println("*** change to banner, minimize, mini_window OK ***");
		
		// 다시 채팅창 안으로
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		pause(1500);
		
		// 팝업 버튼 클릭
		driver.findElementByClassName("view_mode_mini_window").findElement(By.className("popup")).click();
		System.out.println("popup btn click");
		pause(3000);
		
		String oriWindow = driver.getWindowHandle();
		driver.switchTo().window("CDwindow-1");
		System.out.println("switch window to popup");
		pause(2000);
		
		// 상담사가 상담을 받기 전, 몇명의 고객이 기다립니다. 창이 있으면
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
			System.out.println("_t_close click!! in popup");
		}
		pause(2000);
		
		// 팝업창 닫기
		driver.findElementByClassName("view_mode_popup").findElement(By.className("_t_close")).click();;
		System.out.println("*** close popup ***");
		
		System.out.println("*** change to popup OK ***");
		pause(2000);
		
		driver.switchTo().window(oriWindow);
		pause(2000);
		

		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		pause(1000);
		
		// 이모티콘 전송1 (전송버튼)
		driver.findElementById("btnEmoticon").click();
		pause(3000);
		
		List<WebElement> emoList = driver.findElementsByClassName("owl-item");
		emoList.get(1).click();
		pause(2000);
		
		driver.findElementById("sendBtn").click();
		pause(2000);
		
		// 고객이 보낸 메시지가 총 3개이면. (상담신청, 메시지전송, 이모티콘)
		assertEquals(3, driver.findElementsByClassName("customer").size());		
		System.out.println("*** emoticon send (send btn) ***");
		
//		// 이모티콘 전송2 (이모티콘 두번 눌러서)
//		driver.findElementById("message").clear();
//		driver.findElementById("btnEmoticon").click();
//		emoList = driver.findElementsByClassName("owl-item");
////		emoList.get(2).click();
//		Point emoP = emoList.get(2).getLocation();
//		
//		preContext = driver.getContext();
//		driver.context("NATIVE_APP");
//		pause(3000);
//		driver.tap(3, emoP.getX(), emoP.getY(), 1000);
//		System.out.println("이모티콘 더블클릭 전송");
//		pause(1000);
//		driver.context(preContext);
		
//		pause(2000);
//		driver.findElementById("sendBtn").click();
//		System.out.println("emoticon send 2");
//		System.out.println("****이모티콘 전송 끝****");
		
        // 사진첨부 -> 디바이스마다 변경해 줄 필요성이 있음
        System.out.println(driver.getContextHandles());
        driver.findElementById("fileAttach").click();
        System.out.println("fileAttach btn click");
        pause(2000);
        driver.context("NATIVE_APP");
        pause(2000);
        
        if (driver.findElementByName("카메라").isDisplayed())
        {
        	driver.findElementByName("카메라").click(); // 카메라 클릭 カメラ
        	System.out.println("Camera click");
        	pause(2000);
        	
        	// 카메라 셔터 클릭 -> 디바이스마다 변경해 줄 필요성이 있음
        	pause(4000);
        	driver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
        	System.out.println("Camera shot click");
        	pause(6000);
//        driver.findElementById("com.android.camera2:id/done_button").click();
        	driver.findElementById("com.android.camera2:id/done_button").click(); // testobject nexus 4
        	System.out.println("done 버튼 클릭");
        	pause(4000);        	
        }
        
        else if (driver.findElementByName("Android 시스템").isDisplayed())
        {
        	driver.findElementByName("Android 시스템").click(); // 안드로이드 시스템 클릭
        	pause(3000);
        	
        	driver.findElementByName("카메라").click(); // 카메라 클릭 カメラ
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
        }
        
        // 다시 웹으로 변경
        driver.context("WEBVIEW_1");

				
		///////////////// 팝업창 챗팅 시작 ////////////////////
		// 팝업으로 전환		
		driver.findElementByClassName("view_mode_mini_window").findElement(By.className("popup")).click();
		pause(4000);
		
		// 원래 프레임으로 돌아오기
		driver.switchTo().parentFrame();
		
		// window 전환
		oriWindow = driver.getWindowHandle();
		driver.switchTo().window("CDwindow-2");
		pause(3000);
		System.out.println("switch window");
		
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
		}
				
		driver.findElementById("message").sendKeys(MSG_SEND_POPUP);
		driver.findElementById("sendBtn").click();
		
		// hide keyboard; some device cannot click _t_close because of software keyboard
		pause(1000);
		preContext = driver.getContext();
		driver.context("NATIVE_APP");
		pause(1000);
		driver.pressKeyCode(AndroidKeyCode.BACK); // for hide keyboard
		pause(1000);
		
		driver.context(preContext);
		pause(1000);		
		System.out.println("message send");


		assertEquals(true, driver.getPageSource().contains(MSG_SEND_POPUP));
		System.out.println("***메시지 전송 성공 (팝업창)***");
		
		// 이모티콘 전송1 - 팝업창 (전송버튼)
		driver.findElementById("btnEmoticon").click();
		pause(3000);
		
		emoList = driver.findElementsByClassName("owl-item");
		emoList.get(0).click();
		pause(2000);
		driver.findElementById("sendBtn").click();
		pause(2000);
		System.out.println("emoticon send 1");
		
//		// 이모티콘 전송2 - 팝업창 (이모티콘 두번 눌러서)
//		driver.findElementById("message").clear();
//		driver.findElementById("btnEmoticon").click();
//		emoList.get(3).click();
//		emoList.get(3).click();
//		pause(2000);
//		System.out.println("emoticon send 2");
//		takeScreenshot();
		
		// 다시 작은창으로
		driver.findElementByClassName("view_mode_popup").findElement(By.className("_t_close")).click();
		pause(3000);
		System.out.println("close popup");
		
		///////////////// 팝업창 챗팅 끝 ////////////////////
				
		// 다시 원래 윈도우로 돌아오기
		driver.switchTo().window(oriWindow);
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
		takeScreenshot();
		
		
		// 설문이 있는 경우
		if(driver.findElementById("surveyLayer").isDisplayed() == true)
		{			
			driver.findElementById("surveyLayer").findElements(By.className("star_off")).get(4).click();
			pause(1500);
			System.out.println("별점주기");
			
			driver.findElementById("surveyMsg").findElement(By.id("option01")).sendKeys("상담 감사합니다.");
			pause(2000);
			driver.findElementById("surveyLayer").findElement(By.className("btn_end")).click();
			pause(2000);
			
			// 마지막 팝업창 닫기
			driver.context("NATIVE_APP");
			pause(1000);
			System.out.println("context switch to NATIVE_APP");
					
			driver.findElementById("android:id/button1").click();
			pause(1000);
			System.out.println("popup 확인 클릭");
					
			System.out.println("상담 종료");
			pause(1000);
		}
		// 설문이 없는 경우 (상담사가 상담을 받지 않으면 채팅 종료 후 설문이 없다.)
		else
		{
			System.out.println("설문 없이 종료");
		}
		
		
		// 종료 후 종료메시지가 정상적으로 나가는지 확인
		pause(2000);
		assertEquals(true, driver.getPageSource().contains(MSG_END));

	}

	/**
	 * 전달되는 밀리세컨트 만큼 멈추기
	 * 
	 * @param millisec
	 */
	public static void pause(int millisec) 
	{
		try 
		{
			Thread.sleep(millisec);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public boolean takeScreenshot() throws IOException 
	{		
		// PC에 스크린샷 저장
		String contextName = driver.getContext();
		driver.context("NATIVE_APP"); // 네이티브 앱으로 전환
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
		
		// PC에 스크린샷 파일 저장 //
		File location = new File("screenshots");
		String screenShotName = location.getAbsolutePath() + File.separator + DEVICE_NAME + "_" + screenShotNumber + ".png";
		FileUtils.copyFile(screenShot, new File(screenShotName));
		screenShotNumber++;
		// PC에 스크린샷 파일 저장 //
		
		driver.context(contextName); // 이전 컨텍스트로 복귀
		
		// Amazon Device Farm 스크린샷 저장
		screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", getTimestamp())));
	}
	
	private long getTimestamp() 
	{
		return new Date().getTime();
	}
	
	private boolean containPageSource (String source)
	{
		return driver.getPageSource().contains(source);
	}

}
