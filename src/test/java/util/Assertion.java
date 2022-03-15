package util;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class Assertion extends TestBase {

	
	public static void logoDisplayTest() throws InterruptedException {

		TestUtils.testTitle("To verify that GTBank Logo is displayed");
		Thread.sleep(500);
		WebElement img = getDriver().findElement(By.xpath("//a/img[@alt='GTBank Logo']"));

		String deviceImg = img.getAttribute("src");
		String validImage = "app-logo.svg";
		String emptyImage = "image";

		if (deviceImg.endsWith(validImage)) {
			testInfo.get().info("Logo is displayed");
		} else if (deviceImg.endsWith(emptyImage)) {
			testInfo.get().error("Logo is not displayed");
		} 
	}
	
	public static void assertCardDetails() throws InterruptedException {

		String accBalance = getDriver().findElement(By.xpath("//h3/span")).getText();
		String bookBalance = getDriver().findElement(By.xpath("//mat-card-content/div/p/span")).getText();
		String accNum = getDriver().findElement(By.xpath("//mat-card-footer/p")).getText();
		String name = getDriver().findElement(By.xpath("//p[2]")).getText();
		String accType = getDriver().findElement(By.xpath("//p[3]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Account Balance", accBalance);
		fields.put("Book Balance", bookBalance);
		fields.put("Account Number", accNum);
		fields.put("Account Name", name);
		fields.put("Account Type", accType);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}
	
	public static void assertMenuBarDetails() throws InterruptedException {

		String name = getDriver().findElement(By.xpath("//div[2]/p")).getText();
		String bvn = getDriver().findElement(By.xpath("//div[2]/span")).getText();
		String nin = getDriver().findElement(By.xpath("//span[2]")).getText();
		String lastLogin = getDriver().findElement(By.xpath("//span[3]")).getText();
		

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Name", name);
		fields.put("BVN", bvn);

		if (nin.contains("Last Login:")) {
			fields.put("Last Login date", nin);
		} else {
			fields.put("NIN", nin);
			fields.put("Last Login date", lastLogin);
		}

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}
	
	public static void userProfileImageDisplayTest() throws InterruptedException {

		TestUtils.testTitle("To confirm that user's profile Image is displayed");
		Thread.sleep(500);
		WebElement img = getDriver().findElement(By.xpath("//*[@id='main_navbar']/div/div[1]/div[1]/div"));

		String deviceImg = img.getAttribute("style");
		String validImage = "data:image/";
		String emptyImage = "assets/images/placeholder.png";

		if (deviceImg.contains(validImage)) {
			testInfo.get().info("Picture is displayed");
		} else if (deviceImg.contains(emptyImage)) {
			testInfo.get().error("Picture is empty");
		} 
	}
	
	public static void assertAccountOfficersDetails() throws InterruptedException {

		String name = getDriver().findElement(By.xpath("//h3")).getText();
		String address = getDriver().findElement(By.xpath("//mat-expansion-panel/div/div/div/span")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Name", name);
		fields.put("Address", address);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void accountOfficersProfileImageDisplayTest() throws InterruptedException {

		WebElement img = getDriver().findElement(By.xpath("//mat-expansion-panel/div/div/div/div"));
	
		String deviceImg = img.getAttribute("style");
		String validImage = "assets/images/user-card/AcctMgr.png";
		String emptyImage = "assets/images/placeholder.png";

		if (deviceImg.contains(validImage)) {
			testInfo.get().info("Picture is displayed");
		} else if (deviceImg.contains(emptyImage)) {
			testInfo.get().error("Picture is empty");
		} 
	}
	
	public static void assertAccountStatementTransactionDetails() throws InterruptedException {

		String amount = getDriver().findElement(By.xpath("//h3")).getText();
		String date = getDriver().findElement(By.xpath("//mat-card/div[2]/p")).getText();
		String transDetails = getDriver().findElement(By.xpath("//mat-card/p")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Amount", amount);
		fields.put("Date/Time", date);
		fields.put("Transaction Details", transDetails);
		
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}
}
