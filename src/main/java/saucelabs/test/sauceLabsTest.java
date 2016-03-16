package saucelabs.test;

import static org.junit.Assert.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

public class sauceLabsTest {
	
	AppiumDriver driver;

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.4.10");
		caps.setCapability("deviceName","Samsung Galaxy S4 GoogleAPI Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "Browser");
		caps.setCapability("platformVersion", "4.4");
		caps.setCapability("platformName","Android");
		
		driver = new AndroidDriver(new URL("https://ondemand.saucelabs.com:80"), caps);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		driver.get("http://www.google.com");
		pause(3000);
		System.out.println("google connected");
//		driver.get("http://www.spectra.co.kr");
//		pause(3000);
		
//		driver.findElementByLinkText("About Spectra").click();
//		pause(3000);
		
		driver.findElementById("lst-ib").sendKeys("spectra0");
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
