package appimTest;

import static org.junit.Assert.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.common.TestObject;
import org.testobject.appium.junit.TestObjectTestResultWatcher;


public class TestObject_test {
	private AppiumDriver driver;
	//private AppiumDriver driver;
//	AndroidDriver<WebElement> driver;
	
	// Sets the test name to the name of the test method
	@Rule
	public TestName testName = new TestName();
	
	// Takes care of sending the result of the tests ofver to TestObject
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	

	// This is the setup that will be run before the test
	@Before
	public void setUp() throws Exception {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("testobject_api_key", "FA7C351F9E3746A3A81CDCF35A89E2A9");
		capabilities.setCapability("testobject_app_id", "1");
//		capabilities.setCapability("testobject_device", "LG_Nexus_4_E960_real");
		capabilities.setCapability("testobject_device", "Asus_Google_Nexus_7_real");
//		capabilities.setCapability("testobject_appium_version", "1.4.1");
//		capabilities.setCapability("testobject_suite_name", "Default Appium Suite");
//		capabilities.setCapability("testobject_test_name", "Default Appium Test");
//		capabilities.setCapability("app", "browser");
		
		System.out.println("before connect driver");
		driver = new AndroidDriver<WebElement>(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
//		driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
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
	public void testMethod() {
		
		driver.get("http://www.google.com");
		pause(3000);
		System.out.println("google connected");
//		driver.get("http://www.spectra.co.kr");
//		pause(3000);
		
//		driver.findElementByLinkText("About Spectra").click();
//		pause(3000);
		
		driver.findElementById("lst-ib").sendKeys("spectra");
		pause(2000);
		System.out.println("input spectra");
		
		driver.findElementByName("btnG").click();
		pause(3000);
		System.out.println("click btnG");
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
