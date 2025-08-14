package TestScript;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Keywords {
	private static final Logger logger = LogManager.getLogger(Keywords.class);
	private WebDriver driver;
	private Properties prop;
	private FileInputStream file;
	private Select select;

	public void Openbrowser() throws Exception {
		FirefoxOptions options = new FirefoxOptions();
		logger.info("Initializing Firefox options");

		// Set headless mode
		options.setHeadless(false);

		String isDocker = System.getenv("IS_DOCKER");

		if ("true".equals(isDocker)) {
			logger.info("Running in Docker environment");
			options.setBinary("/usr/bin/firefox");
			file = new FileInputStream("/app/src/main/java/ObjectRepository/objectrepository.properties");
		} else {
			logger.info("Running on local machine");
			options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
			file = new FileInputStream("src/main/java/ObjectRepository/objectrepository.properties");
		}

		// Add preferences
		options.addPreference("general.useragent.override",
				"Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Mobile/14E5239e Safari/602.1");
		options.addPreference("network.stricttransportsecurity.preloadlist", false);
		options.addPreference("security.ssl.enable_ocsp_stapling", false);
		options.addPreference("network.cookie.cookieBehavior", 0);  // 0 = Accept all cookies (including third-party)
		options.addPreference("network.cookie.lifetimePolicy", 2);  // 2 = Accept cookies from all origins
		options.addPreference("network.cookie.sameSite.none", true);  // Allow SameSite=None cookies
		options.addPreference("network.cookie.secure", true);  // Enforce Secure cookies

		// Launch the browser
		driver = new FirefoxDriver(options);
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 812));
		logger.info("Browser launched with window size: 375x812");

		// Load properties
		prop = new Properties();
		prop.load(file);
		logger.info("Object repository properties loaded");
	}

	public void refresh() throws Exception {
		logger.info("Refreshing page");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.navigate().refresh();
	}

	public void navigate1(String data) throws Exception {
		logger.info("Navigating to URL: " + data);
		driver.get(data);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void navigate2(String data) throws Exception {
		logger.info("Navigating to URL with delay: " + data);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.get(data);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void iframe(String data) throws Exception {
		logger.info("Switching to iFrame: " + data);
		driver.switchTo().frame(data);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void defaultContent() throws Exception {
		logger.info("Navigating to URL with delay: " );
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void scroll1() throws Exception {
		logger.info("Scrolling down by 1000 pixels");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 1000);");
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void scroll2(String objectname) throws Exception {
		logger.info("Scrolling to object: " + objectname);
		Thread.sleep(5000);
		WebElement element = driver.findElement(By.xpath(prop.getProperty(objectname)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		js.executeScript("arguments[0].click();", element);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void input(String data, String objectname) throws Exception {
		logger.info("Inputting data into: " + objectname);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty(objectname))).sendKeys(data);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void click1(String objectname) throws Exception {
		logger.info("Clicking object: " + objectname);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(1000);
		String xpath = prop.getProperty(objectname);
		if (xpath == null) {
			logger.error("Cannot find elements when the XPath is null for key: " + objectname);
			throw new IllegalArgumentException("Cannot find elements when the XPath is null for key: " + objectname);
		}
		driver.findElement(By.xpath(xpath)).click();
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void click2(String objectname) throws Exception {
		logger.info("Clicking object with delay: " + objectname);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.xpath(prop.getProperty(objectname))).click();
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void dropdown(String objectname) throws Exception {
		logger.info("Selecting dropdown item in: " + objectname);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		select = new Select(driver.findElement(By.xpath(prop.getProperty(objectname))));
		select.selectByIndex(1);
		logger.info("Current URL: " + driver.getCurrentUrl());
	}

	public void alert() throws Exception {
		logger.info("Handling alert");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(1000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		logger.info("Alert handled");
	}

	public void verifybrokenimages() throws Exception {
		logger.info("Verifying broken images");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
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
					logger.info(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> OK");
				} else {
					logger.warn(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> NOT FOUND");
					Thread.sleep(3000);
					driver.close();
				}
				httpURLConnection.disconnect();
			} catch (Exception e) {
				logger.error("Exception while verifying image: " + imageSrc, e);
			}
		}
	}

	public void close() throws Exception {
		logger.info("Closing browser");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.close();
	}

	public void capture1() throws Exception {
		logger.info("Capturing screenshot of payment success page");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(45000);
		Date currentdate = new Date();
		String dateString = currentdate.toString().replace(" ", "-").replace(":", "-");
		String screenshotfilename = "payment_success_" + dateString;
		File screenshotfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotfile, new File("src/main/java/Screenshots/" + screenshotfilename + ".png"));
		logger.info("Screenshot saved: " + screenshotfilename);
	}

	public void capture2() throws Exception {
		logger.info("Capturing screenshot of gift card details");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(10000);
		Date currentdate = new Date();
		String dateString = currentdate.toString().replace(" ", "-").replace(":", "-");
		String screenshotfilename = "GC_details_" + dateString;
		File screenshotfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotfile, new File("src/main/java/Screenshots/" + screenshotfilename + ".png"));
		logger.info("Screenshot saved: " + screenshotfilename);
	}

	public String verifytitle() throws Exception {
		logger.info("Verifying page title");
		return driver.getTitle();
	}

	public String verifyurl() throws Exception {
		logger.info("Verifying page URL");
		return driver.getCurrentUrl();
	}

	public String verifyeditbox(String objectname) throws Exception {
		logger.info("Verifying edit box value for: " + objectname);
		return driver.findElement(By.xpath(prop.getProperty(objectname))).getAttribute("value");
	}
}