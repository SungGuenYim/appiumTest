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
 * �ȵ���̵� �� ���ķ����� �׽�Ʈ
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
		// URL ȣ��
		driver.get("http://111.90.160.180:17070/x/sph.jsp");
		pause(3000);
		
		// ������ ����
		driver.findElementByLinkText("1. domain : eonet").click();
		pause(2500);
		
		// ��� ����� �� ���� ��ٸ���
		System.out.println("��� ����? " + driver.getPageSource().contains("_t_banner"));
		while (!driver.getPageSource().contains("_t_banner"))
		{
			pause(1500);
			System.out.println("��� ���� �ȶ�");
			// 15�ʸ� ��ٷ��� ���� ������ ������ ����
			failCount++;
			assertTrue(failCount < 10);
		}
		System.out.println("��� ����");
		failCount = 0;
				
		// ��ʰ� ä��â���� �ٲ� �� ���� ��ٸ���
		System.out.println("ä��â ����? " + driver.getPageSource().contains("_t_mini_window"));
		while (!driver.getPageSource().contains("_t_mini_window"))
		{
			pause(1500);
			System.out.println("ä��â ���� �ȶ�");
			// 15�ʸ� ��ٷ��� ���� ������ ������ ����
			failCount++;
			assertTrue(failCount < 10);
		}
		System.out.println("ä��â ����");
		failCount = 0;
		// ���Ŭ��
		
		///////////////////////////////////////////////////////////////
		// iframe �� (�� ��)���� ����
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		System.out.println("frame switch");
		pause(5000);
	
		driver.findElementById("message").sendKeys("������׽�Ʈ�ڵ�ȭ���Դϴ�.");
		driver.findElementById("sendBtn").click();
		pause(5000);
		
		// ���簡 ����� �ޱ� ��, ����� ���� ��ٸ��ϴ�. â�� ������
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
			System.out.println("_t_close click!!");
		}
		pause(3000);
		
		/////////////////////////////
		
		// �ּ�ȭ 
		driver.findElementByClassName("icon_btn").findElement(By.className("minimize")).click();
		System.out.println("minimize click");
		pause(3000);
				
		// ���� frame���� ���ƿ��� (��� ������ ���ؼ�)
		driver.switchTo().defaultContent();
		System.out.println("switch to mainFrame");
		pause(3000);
		
		// �ٽ� ��� Ŭ���ؼ� ä��â����
		driver.findElementByClassName("_t_banner_img").click();
		System.out.println("banner click");
		pause(3000);
		
		// �ٽ� ä��â ������
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
		
		
		// �̸�Ƽ�� ����1 (���۹�ư) -> ios���� �̸�Ƽ�� ������ �ȵ�. -> ��ǥ�� ����
		driver.findElementById("btnEmoticon").click();
		System.out.println("btnEmotion click");
		pause(6000);

		// �̸�Ƽ�� ��ǥ�� Ŭ��
		
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
		System.out.println("NATIVE_APP���� ��ȯ");
		pause(5000);
		driver.tap(1, 63, 360, 50);
		System.out.println("�̸�Ƽ�� ����");
		pause(3000);
		
//		driver.switchTo().defaultContent();
		driver.context("WEBVIEW_1");
		System.out.println("WEBVIEW context�� ��ȯ");
		pause(5000);
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		System.out.println("frame ������ ������");
		pause(5000);
		
		
		driver.findElementById("sendBtn").click();
		pause(2000);
		System.out.println("�̸�Ƽ�� ��ǥ�� ���� ����");
		
		
//		emoList.get(1).click();
//		System.out.println("emoticon click");
//		pause(2000);
		
		
		driver.findElementById("message").sendKeys("�̸�Ƽ�� ����!!");
		pause(2000);
		driver.findElementById("sendBtn").click();
		System.out.println("send btn click");
		pause(2000);
		System.out.println("emoticon send 1");
		
		driver.findElementById("message").sendKeys("after minimize chat");
		driver.findElementById("sendBtn").click();
		pause(2000);
		
//		// �̸�Ƽ�� ����2 (�̸�Ƽ�� �ι� ������) -> ios���� �̸�Ƽ�� ������ �ȵ�
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
		
		///////////////// �˾�â ê�� ���� ////////////////////
//		System.out.println("before click popup btn");
//		pause(2000);
		
		// ios���� �˾�â�� �ȶ�... dc.setCapability("safariAllowPopups", true); �ȸ��� �Ф�
		driver.findElementByClassName("icon_btn").findElement(By.className("popup")).click();
		pause(10000);
		System.out.println("popup btn click");
		
		// window ��ȯ
		String oriWindow = driver.getWindowHandle();
//		driver.switchTo().window("1");
		pause(3000);
		System.out.println("auto switch window?");
		
		if(driver.findElementById("closeLayer").findElement(By.className("_t_close")).isDisplayed())
		{
			driver.findElementById("closeLayer").findElement(By.className("_t_close")).click();			
			pause(2000);
		}
				
		driver.findElementById("message").sendKeys("������׽�Ʈ�ڵ�ȭ���Դϴ�.");
		driver.findElementById("sendBtn").click();
		pause(3000);
		System.out.println("message send");
//		
//		// �̸�Ƽ�� ����1 - �˾�â (���۹�ư) - ios���� �̸�Ƽ�� Ŭ���� �ȵ�
//		driver.findElementById("btnEmoticon").click();
//		pause(3000);
//		
////		emoList = driver.findElementsByClassName("owl-item");
////		emoList.get(1).click();
//		pause(2000);
//		driver.findElementById("sendBtn").click();
//		pause(2000);
//		System.out.println("emoticon send 1");
		
//		// �̸�Ƽ�� ����2 - �˾�â (�̸�Ƽ�� �ι� ������) - ios���� �̸�Ƽ�� Ŭ���� �ȵ�
//		driver.findElementById("message").clear();
//		driver.findElementById("btnEmoticon").click();
//		pause(2000);
//		emoList.get(2).click();
//		pause(2000);
//		emoList.get(2).click();
////		driver.findElementById("sendBtn").click();
//		pause(2000);
//		System.out.println("emoticon send 2");
		
		// �ٽ� ����â����
		driver.findElementByClassName("icon_btn").findElement(By.className("_t_close")).click();
		pause(3000);
		System.out.println("close popup");
		
		///////////////// �˾�â ê�� �� ////////////////////
		
		
		// �ٽ� ���� ������� ���ƿ���
//		driver.switchTo().window(oriWindow);
		driver.switchTo().defaultContent();
		pause(3000);
		
		// iframe �� (�� ��)���� ����
		driver.switchTo().frame(driver.findElementByClassName("_t_mini_window_frame"));
		
		// ��� �����ϱ�
		driver.findElementById("btnChend").click();
		pause(2000);
		System.out.println("end counselor");
		
		driver.findElementByClassName("btn_end").click();
		pause(2000);
		System.out.println("btn_end click");
		
		// ������ �ִ� ���
		if(driver.findElementById("surveyLayer").isDisplayed() == true)
		{			
			driver.findElementById("surveyLayer").findElement(By.className("star_off")).click();
			pause(1500);
			System.out.println("�����ֱ�");
			
			driver.findElementById("surveyMsg").findElement(By.id("option01")).sendKeys("��� �����մϴ�.");
			pause(2000);
			driver.findElementById("surveyLayer").findElement(By.className("btn_end")).click();
			pause(2000);
			
			// ������ �˾�â �ݱ�
//			driver.context("NATIVE_APP");
//			pause(5000);
//			System.out.println("context switch to NATIVE_APP");
//			
////			driver.switchTo().alert().accept();
////			driver.findElementByName("OK").click();
//			System.out.println("system alert OK");
//			pause(3000);
			
			System.out.println("���� �� �׽�Ʈ ����");

		}
		// ������ ���� ��� (���簡 ����� ���� ������ ä�� ���� �� ������ ����.)
		else
		{
			System.out.println("���� ���� ����");
		}

		
	}

	/**
	 * ���޵Ǵ� �и�����Ʈ ��ŭ ���߱�
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

