package testObject;

import static org.junit.Assert.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.net.URL;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testobject.appium.common.TestObject;
import org.testobject.appium.junit.TestObjectTestResultWatcher;


public class WebViewTest {

//	AndroidDriver<WebElement> driver;
	private AndroidDriver<WebElement> driver;
	
	// Sets the test name to the name of the test method
	@Rule
	public TestName testName = new TestName();
	
	// Takes care of sending the result of the tests ofver to TestObject
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	

	// This is the setup that will be run before the test
	@Before
	public void setUp() throws Exception {
		
        File appDir = new File("src");
        File app = new File(appDir, "TestWebViewTmp.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "5000");               
        cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);

		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		
//		// demo page test
//		capabilities.setCapability("testobject_api_key", "621F09CD4D0A43FAA670A97EB31B4E6B"); // web
//		capabilities.setCapability("testobject_app_id", "1");
//		capabilities.setCapability("testobject_device", "LG_Nexus_4_E960_real");
//		
//		// web
////		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Browser");
//		
//		
//		// native app 
//		capabilities.setCapability("app", "TestWebViewTmp");
////		capabilities.setCapability("appPackage", "com.example.testforappium");
////		capabilities.setCapability("appActivity", ".MainActivity");
//		///////////////////////////////////////////////////////////
//		
//		capabilities.setCapability("testobject_appium_version", "1.4.1");
//		
//		System.out.println("before connect driver");
//		driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
//		System.out.println("after connect driver. success!!");
//		
//		// IMPORTANT!! We need to provide the Watcher with our initialized AppiumDriver
//		resultWatcher.setAppiumDriver(driver);
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
	public void testMethod() {
		
		System.out.println("*******************Test Start*******************");
		pause(5000);
//        Set<String> contextNames = driver.getContextHandles();
        System.out.println("contexts: " + driver.getContextHandles()); // prints out something like
//        System.out.println("windows: " + driver.getWindowHandles());
        System.out.println("current window: " + driver.getWindowHandle());

        driver.context("WEBVIEW_kr.co.spectra"); // set context to WEBVIEW_1

        // do some web testing
        new Select(driver.findElementById("category")).selectByValue("NODE0000000003");
        pause(2000);
        driver.findElementById("content").sendKeys("hello!!!!");
        pause(2000);
        driver.findElementByLinkText("채팅시작").click();
        pause(2000);
        driver.findElementById("layerConfirm").click();

        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("11");
        driver.findElementById("sendBtn").click();
        pause(2000);
        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("222");
        driver.findElementById("sendBtn").click();
        pause(2000);
        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("333");
        driver.findElementById("sendBtn").click();
        pause(2000);
        driver.findElementById("message").clear();
        driver.findElementById("message").sendKeys("444");
        driver.findElementById("sendBtn").click();
        pause(2000);
        driver.findElementById("btnMenu").click();
        pause(2000);
        driver.findElementByLinkText("상담종료").click();
        pause(2000);
        driver.findElementByLinkText("예").click();
        pause(2000);

        driver.context("NATIVE_APP");
        // do more native testing if we want
        pause(2000);

    	
    	System.out.println("end test~!!");
	}
	
	public static void pause(int millisec) 
	{
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
