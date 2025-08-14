package TestScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExecuteTest extends Keywords {

	private static final Logger logger = LogManager.getLogger(ExecuteTest.class);
	private static final String LOG_FILE_PATH = "logs/app.log";
	private static ExtentReports extent;
	private static ExtentTest test;

	@BeforeClass
	public void setupReport() {
		// Clear screenshots folder
		clearScreenshotsFolder();

		// Setup Extent Reports
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/ExecuteTestReport.html");

		// Set the theme to DARK
		sparkReporter.config().setTheme(Theme.DARK);

		// Optional: Set additional configurations
		sparkReporter.config().setDocumentTitle("Test Execution Report");
		sparkReporter.config().setReportName("Automation Test Results");

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	}

	public void clearScreenshotsFolder() {
		String folderPath = "src/main/java/Screenshots";
		File folder = new File(folderPath);

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						if (file.delete()) {
							logger.info("Deleted screenshot file: " + file.getName());
						} else {
							logger.warn("Failed to delete screenshot file: " + file.getName());
						}
					}
				}
			}
		} else {
			logger.warn("Screenshot folder does not exist or is not a directory.");
		}
	}

	@Test
	public void leadtest() {

		test = extent.createTest("Lead Test");
		logger.info("Starting the lead test");
		test.log(Status.INFO, "Starting the lead test");

		Keywords key = new Keywords();
		FileInputStream f = null;
		XSSFWorkbook b = null;

		try {
			f = new FileInputStream(new File("src/main/java/TestSuite/TestCase.xlsx"));
			b = new XSSFWorkbook(f);
			test.log(Status.INFO, "Excel file loaded successfully");

			// Loop through all sheets in the workbook
			for (int j = 0; j < b.getNumberOfSheets(); j++) {
				XSSFSheet s = b.getSheetAt(j);

				// Create a new test using the sheet name
				String sheetName = s.getSheetName();
				test = extent.createTest(sheetName);
				test.log(Status.INFO, "Processing sheet: " + sheetName);
				logger.info("Processing sheet: " + sheetName);

				ArrayList<Object> a = new ArrayList<>(); // Reset array for each sheet

				Iterator<?> itr = s.iterator();
				while (itr.hasNext()) {
					XSSFRow row = (XSSFRow) itr.next();
					Iterator<?> cell = row.cellIterator();
					while (cell.hasNext()) {
						XSSFCell celldata = (XSSFCell) cell.next();
						switch (celldata.getCellType()) {
							case STRING:
								a.add(celldata.getStringCellValue());
								logger.debug("Added string value: " + celldata.getStringCellValue());
								break;
							case NUMERIC:
								a.add(celldata.getNumericCellValue());
								logger.debug("Added numeric value: " + celldata.getNumericCellValue());
								break;
							case BOOLEAN:
								a.add(celldata.getBooleanCellValue());
								logger.debug("Added boolean value: " + celldata.getBooleanCellValue());
								break;
							default:
								logger.warn("Unknown cell type");
						}
					}
				}

				for (int i = 0; i < a.size() - 3; i++) {
					// Ensure that there are enough elements to proceed with the current iteration
					if (i + 3 < a.size()) {
						String keyword = (String) a.get(i);
						String data = (String) a.get(i + 1);
						String objectname = (String) a.get(i + 2);
						String runmode = (String) a.get(i + 3);

						logger.info("Processing keyword: " + keyword + ", data: " + data + ", objectname: " + objectname
								+ ", runmode: " + runmode);
						test.log(Status.INFO, "Processing keyword: " + keyword + ", data: " + data + ", objectname: "
								+ objectname + ", runmode: " + runmode);

						if (runmode.equalsIgnoreCase("yes")) {
							int retryCount = 0;
							boolean success = false;

							while (retryCount < 3 && !success) {
								try {
									switch (keyword) {
										case "Openbrowser":
											logger.info("Executing Openbrowser");
											test.log(Status.INFO, "Executing Openbrowser");
											key.Openbrowser();
											test.pass("Browser opened successfully");
											break;
										case "refresh":
											logger.info("Executing refresh");
											test.log(Status.INFO, "Executing refresh");
											key.refresh();
											test.pass("Refreshed successfully");
											break;
										case "navigate1":
											logger.info("Executing navigate1");
											test.log(Status.INFO, "Executing navigate1");
											key.navigate1(data);
											test.pass("Navigated successfully");
											break;
										case "navigate2":
											logger.info("Executing navigate2");
											test.log(Status.INFO, "Executing navigate2");
											key.navigate2(data);
											test.pass("Navigated successfully");
											break;
										case "iframe":
											logger.info("Switching to iFrame");
											test.log(Status.INFO, "Switching to iFrame");
											key.iframe(data);
											test.pass("Switched successfully");
											break;
										case "defaultContent":
											logger.info("Executing navigate2");
											test.log(Status.INFO, "Executing navigate2");
											key.defaultContent();
											test.pass("Switched successfully");
											break;
										case "scroll1":
											logger.info("Executing scroll1");
											test.log(Status.INFO, "Executing scroll1");
											key.scroll1();
											test.pass("Scrolled successfully");
											break;
										case "scroll2":
											logger.info("Executing scroll2");
											test.log(Status.INFO, "Executing scroll2");
											key.scroll2(objectname);
											test.pass("Scrolled successfully");
											break;
										case "input":
											logger.info("Executing input with data: " + data + " and objectname: "
													+ objectname);
											test.log(Status.INFO, "Executing input with data: " + data + " and objectname: "
													+ objectname);
											key.input(data, objectname);
											test.pass("Typed successfully");
											break;
										case "click1":
											logger.info("Executing click1 on object: " + objectname);
											test.log(Status.INFO, "Executing click1 on object: " + objectname);
											key.click1(objectname);
											test.pass("Clicked successfully");
											break;
										case "click2":
											logger.info("Executing click2 on object: " + objectname);
											test.log(Status.INFO, "Executing click2 on object: " + objectname);
											key.click2(objectname);
											test.pass("Clicked successfully");
											break;
										case "dropdown":
											logger.info("Executing dropdown on object: " + objectname);
											test.log(Status.INFO, "Executing dropdown on object: " + objectname);
											key.dropdown(objectname);
											test.pass("Selected successfully");
											break;
										case "alert":
											logger.info("Handling alert");
											test.log(Status.INFO, "Handling alert");
											key.alert();
											test.pass("Handled Alert successfully");
											break;
										case "verifybrokenimages":
											logger.info("Verifying broken images");
											test.log(Status.INFO, "Verifying broken images");
											key.verifybrokenimages();
											test.pass("Verified images successfully");
											break;
										case "close":
											logger.info("Closing browser");
											test.log(Status.INFO, "Closing browser");
											key.close();
											test.pass("Closed Browser successfully");
											break;
										case "capture1":
											logger.info("Capturing screenshot (capture1)");
											test.log(Status.INFO, "Capturing screenshot (capture1)");
											key.capture1();
											test.pass("Captured successfully");
											break;
										case "capture2":
											logger.info("Capturing screenshot (capture2)");
											test.log(Status.INFO, "Capturing screenshot (capture2)");
											key.capture2();
											test.pass("Captured successfully");
											break;
										case "verifytitle":
											logger.info("Verifying title");
											test.log(Status.INFO, "Verifying title");
											String actualTitle = key.verifytitle();
											Assert.assertEquals(data, actualTitle);
											test.pass("Verified Title successfully");
											break;
										case "verifyurl":
											logger.info("Verifying URL");
											test.log(Status.INFO, "Verifying URL");
											String actualURL = key.verifyurl();
											Assert.assertEquals(data, actualURL);
											test.pass("Verified URL successfully");
											break;
										case "verifyeditbox":
											logger.info("Verifying edit box for object: " + objectname);
											test.log(Status.INFO, "Verifying edit box for object: " + objectname);
											String actualValue = key.verifyeditbox(objectname);
											Assert.assertEquals(data, actualValue);
											test.pass("Verified Editbox successfully");
											break;
										default:
											logger.info("Unknown keyword: " + keyword);
											test.log(Status.INFO, "Unknown keyword: " + keyword);
									}
									success = true; // Exit loop if successful
								} catch (Exception e) {
									retryCount++;
									logger.error("Error executing keyword: " + keyword + ", attempt: " + retryCount, e);
									if (retryCount >= 3) {
										logger.error("Test failed after 3 attempts");
										test.log(Status.FAIL, "Test failed after 3 attempts due to exception: " + e.getMessage());
										throw new AssertionError("Stopping test due to multiple failures");
									}
								}
							}
						}
					} else {
						logger.warn("Not enough data to process further keywords");
						break;
					}
				}
			}
			logger.info("Test completed successfully.");
			test.log(Status.PASS, "Test completed successfully.");
		} catch (IOException e) {
			logger.error("Error reading the Excel file", e);
			test.log(Status.FAIL, "Error reading the Excel file: " + e.getMessage());
			Assert.fail("Error reading the Excel file: " + e.getMessage());
		} finally {
			try {
				if (b != null) {
					b.close();
				}
				if (f != null) {
					f.close();
				}
			} catch (IOException e) {
				logger.error("Error closing resources", e);
			}
		}
	}

	@AfterClass
	public void tearDownReport() {
		// Flush the Extent Reports
		extent.flush();
	}
}