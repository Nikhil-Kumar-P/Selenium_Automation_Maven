package com.selenium.automation.project;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

public class Keywords {
	static FirefoxDriver driver;
	static FileInputStream file;
	static Properties prop;
	static Select select;

	public void Openbrowser() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				"/Users/nikhilkumar/Documents/Workspace/aut-lifafaend2end/geckodriver");

		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		file = new FileInputStream(
				"/Users/nikhilkumar/Documents/Workspace/aut-lifafaend2end/src/ObjectRepository/objectrepository.properties");
		prop = new Properties();
		prop.load(file);
	}

	public void navigate1(String data) throws Exception {
		driver.get(data);

	}

	public void navigate2(String data) throws Exception {
		driver.get(data);

	}

	public void input(String data, String objectname) throws Exception {
		driver.findElement(By.xpath(prop.getProperty(objectname))).sendKeys(data);
	}

	public void gettext() throws Exception {
		String actualData = driver.findElement(By.xpath("//*[@id=\"contentBg\"]/div/article")).getText();
		System.out.println(actualData);
		String expectedData = actualData;
		if (actualData.equals(expectedData)) {
			System.out.println("Expected Value is equal to Actual Value");
		} else {
			System.err.println("Expected Value is not equal to Actual Value");
			Thread.sleep(2000);
			driver.close();
		}
	}

	public void scroll() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0,1500)");
	}

	public void input1() throws Exception {
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("12345");
	}

	public void input2() throws Exception {
		if (driver.getPageSource().contains("Mobile Number")) {
			driver.findElement(By.xpath("//*[@id=\"username\"] ")).sendKeys("1234567890");
		} else {
			driver.findElement(By.xpath("//*[@id=\"username\"] ")).sendKeys("automationtester2023@gmail.com");
		}
	}

	public void click1(String objectname) throws Exception {
		driver.findElement(By.xpath(prop.getProperty(objectname))).click();
	}

	public void click2(String objectname) throws Exception {
		driver.findElement(By.xpath(prop.getProperty(objectname))).click();
	}

	public void dropdown(String objectname) throws Exception {
		select = new Select(driver.findElement(By.xpath(prop.getProperty(objectname))));
		select.selectByIndex(1);
	}

	public void close() throws Exception {
		Thread.sleep(15000);
		driver.close();
	}

	public void capture1() throws Exception {
		Thread.sleep(30000);
		Date currentdate = new Date();
		String screenshotfilename = currentdate.toString().replace(" ", "-").replace(":", "-");
		System.out.println(screenshotfilename);
		File screenshotfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotfile,
				new File("C:\\Reports\\Screenshots\\Verified Payment" + screenshotfilename + ".png"));
	}

	public void capture2() throws Exception {
		Thread.sleep(5000);
		Date currentdate = new Date();
		String screenshotfilename = currentdate.toString().replace(" ", "-").replace(":", "-");
		System.out.println(screenshotfilename);
		File screenshotfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotfile,
				new File("C:\\Reports\\Screenshots\\Verified Email" + screenshotfilename + ".png"));
	}

	public void alert() throws Exception {
		Thread.sleep(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
	}

	public void verifybrokenimages() throws Exception {
		Thread.sleep(5000);
		List<WebElement> images = driver.findElements(By.tagName("img"));
		for (WebElement image : images) {
			String imageSrc = image.getAttribute("src");
			try {
				URL url = new URL(imageSrc);
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.connect();

				if (httpURLConnection.getResponseCode() != 404) {
					System.out.println(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> " + "OK");
				} else {
					System.err.println(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> " + "NOT FOUND");
					Thread.sleep(3000);
					driver.close();
				}
				httpURLConnection.disconnect();
			} catch (Exception e) {
				System.out.println(imageSrc);
			}
		}
	}

	public String verifytitle() throws Exception {
		String actualvalue = driver.getTitle();
		return actualvalue;
	}

	public String verifyurl() throws Exception {
		String actualvalue = driver.getCurrentUrl();
		return actualvalue;
	}

	public String verifyeditbox(String objectname) throws Exception {
		String actualvalue = driver.findElement(By.xpath(prop.getProperty(objectname))).getAttribute("value");
		return actualvalue;
	}

}
