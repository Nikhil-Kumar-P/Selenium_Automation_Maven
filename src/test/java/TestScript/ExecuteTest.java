package TestScript;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class ExecuteTest extends Keywords {

	@Test
	public void leadtest() throws Exception {
		Keywords key = new Keywords();
		ArrayList a = new ArrayList();
		FileInputStream f = new FileInputStream(new File("src/main/java/TestSuite/TestCase.xlsx"));
		XSSFWorkbook b = new XSSFWorkbook(f);
		ArrayList<String> l = new ArrayList<String>();
		l.add("Item");
		Scanner sc = new Scanner(System.in);
		int n;
		System.out.println("PES Suite Consists of This Stores");
		for (int i = 0; i < b.getNumberOfSheets(); i++) {
			System.out.println(b.getSheetName(i));
		}
		System.out.println("Please Enter The Number Of Stores To Be Executed From Above Results");
		n = sc.nextInt(1);
		System.out.println("Please Enter The Store ID From Above Results:  ");
		for (int i = 0; i < n; i++) {
			l.add(sc.next("JKNS"));
		}
		for (int i = 0; i < b.getNumberOfSheets(); i++) {
			if (l.contains(b.getSheetName(i))) {
				XSSFSheet s = b.getSheet(b.getSheetName(i));
				Iterator itr = s.iterator();
				while (itr.hasNext()) {
					XSSFRow row = (XSSFRow) itr.next();
					Iterator cell = row.cellIterator();
					while (cell.hasNext()) {
						XSSFCell celldata = (XSSFCell) cell.next();
						switch (celldata.getCellType()) {
						case STRING:
							a.add(celldata.getStringCellValue());
							break;
						case NUMERIC:
							a.add(celldata.getNumericCellValue());
							break;
						case BOOLEAN:
							a.add(celldata.getBooleanCellValue());
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).equals("Openbrowser")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.Openbrowser();
				}
			}
			if (a.get(i).equals("navigate1")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.navigate1(data);
				}
			}
			if (a.get(i).equals("navigate2")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(35000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.navigate2(data);
				}
			}

			if (a.get(i).equals("gettext")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(1000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.gettext();
				}
			}

			if (a.get(i).equals("scroll")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(1000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.scroll();
				}
			}

			if (a.get(i).equals("input")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(1000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.input(data, objectname);
				}
			}

			if (a.get(i).equals("click1")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(1000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.click1(objectname);
				}
			}

			if (a.get(i).equals("click2")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				Thread.sleep(10000);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.click2(objectname);
				}
			}

			if (a.get(i).equals("dropdown")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.dropdown(objectname);
				}
			}
			if (a.get(i).equals("alert")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.alert();
				}
			}

			if (a.get(i).equals("verifybrokenimages")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.verifybrokenimages();
				}
			}

			if (a.get(i).equals("close")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.close();

				}
			}

			if (a.get(i).equals("capture1")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.capture1();

				}
			}
			if (a.get(i).equals("capture2")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					key.capture2();

				}
			}
			if (a.get(i).equals("verifytitle")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					String actualvalue = key.verifytitle();
					Assert.assertEquals(data, actualvalue);
				}
			}

			if (a.get(i).equals("verifyurl")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					String actualvalue = key.verifyurl();
					Assert.assertEquals(data, actualvalue);
				}
			}

			if (a.get(i).equals("verifyeditbox")) {
				String keyword = (String) a.get(i);
				String data = (String) a.get(i + 1);
				String objectname = (String) a.get(i + 2);
				String runmode = (String) a.get(i + 3);
				System.out.println(keyword);
				System.out.println(data);
				System.out.println(objectname);
				System.out.println(runmode);
				if (runmode.equals("yes")) {
					String actualvalue = key.verifyeditbox(objectname);
					Assert.assertEquals(data, actualvalue);
				}
			}
		}
	}
}
