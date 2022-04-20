package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class Dashboard extends TestBase{
	
	@Test
	public void navigateToDashboardTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Dashboard");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Dashboard')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Dashboard')]", "Dashboard");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Quickly perform task from the dashboard or view reports.");
	}
	
	@Test
	public void assertCardCountTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Assert total number of cards");
		int cardCount = getDriver().findElements(By.xpath("//ngu-tile")).size();
		if (TestUtils.isElementPresent("XPATH", "//acct-details/div/div/p")) {
			testInfo.get().info("Total number of Cards displayed: <b>" + cardCount + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		
		TestUtils.testTitle("Assert Details of Card");
		Assertion.assertCardDetails();
		
		TestUtils.testTitle("To confirm that when the 'Show Balance toggle' is OFF, the Account balance and Book balance is masked");
		Assertion.assertCardDetails();
		
		TestUtils.testTitle("To confirm that when the 'Show Balance toggle' is ON, the Account balance and Book balance is displayed");
		// Turn ON the Show Balance Toggle
		getDriver().findElement(By.xpath("//label/div/div/div")).click();
		Thread.sleep(500);
		Assertion.assertCardDetails();
		// Turn OFF the Show Balance Toggle
		getDriver().findElement(By.xpath("//label/div/div/div")).click();
		Thread.sleep(500);
		
		// To confirm that on click of each card, View Account statement page is displayed
		TestUtils.testTitle("To confirm that on click of each card, View Account statement page is displayed");
		getDriver().findElement(By.xpath("//mat-card")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View Statement')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Statement')]", "View Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "View Account Statement, download and share");
		
		// Click on Dashboard
		getDriver().findElement(By.xpath("//a/span[2]")).click();
		Thread.sleep(500);
		
//		// Rater
//		TestUtils.raterTest("Dashboard");
	}

	@Test
	public void menuBarValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		
		TestUtils.testTitle("Assert Menu Bar details");
		Assertion.assertMenuBarDetails();
		Assertion.userProfileImageDisplayTest();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm the search functionality on menu bar");
		testInfo.get().info("<b> Search by Transfer </b>");
		getDriver().findElement(By.xpath("//input[@type='search']")).clear();
		getDriver().findElement(By.xpath("//input[@type='search']")).sendKeys("Transfer");
		TestUtils.assertSearchText("XPATH", "//a/p", "Transfer to GTBank");
		TestUtils.assertSearchText("XPATH", "//a[2]", "Transfer to Other Banks");
		TestUtils.assertSearchText("XPATH", "//a[3]/p", "Transfer to Own Account");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//input[@type='search']")).clear();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the following modules are displayed on the menu bar");
		TestUtils.assertSearchText("XPATH", "//a/span[2]", "Dashboard");
		TestUtils.assertSearchText("XPATH", "//ul[2]/li/a/span[2]", "Accounts");
		TestUtils.assertSearchText("XPATH", "//ul[3]/li/a/span[2]", "Transfers");
		TestUtils.assertSearchText("XPATH", "//ul[4]/li/a/span[2]", "FX Transfers");
		TestUtils.assertSearchText("XPATH", "//ul[5]/li/a/span[2]", "Bills and Payments");
		TestUtils.assertSearchText("XPATH", "//ul[6]/li/a/span[2]", "Airtime/Data Topup");
		TestUtils.assertSearchText("XPATH", "//ul[7]/li/a/span[2]", "Cards");
		TestUtils.assertSearchText("XPATH", "//ul[8]/li/a/span[2]", "Loans and Savings");
		TestUtils.assertSearchText("XPATH", "//ul[9]/li/a/span[2]", "Self Service");
		TestUtils.assertSearchText("XPATH", "//ul[10]/li/a/span[2]", "Log out");
		Thread.sleep(500);
		
		// Theme Customizer
		TestUtils.testTitle("To confirm that there is a Theme Customizer on the dashboard");
		getDriver().findElement(By.xpath("//div/div/div/div[2]/div/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/p")));
		TestUtils.assertSearchText("XPATH", "//li/p", "Theme Customizer");
		TestUtils.assertSearchText("XPATH", "//li[2]/p", "Main layouts");
		TestUtils.assertSearchText("XPATH", "//div/a/span", "Light");
		TestUtils.assertSearchText("XPATH", "//a[2]/span", "Dark");
		TestUtils.assertSearchText("XPATH", "//li[4]/p", "Header color");
		Thread.sleep(500);
		
		// Close Theme Customizer
		getDriver().findElement(By.xpath("//div/div/div/div[2]/div/a")).click();
		Thread.sleep(500);
		
	}
	
	@Test
	public void profileImageUploadTest() throws InterruptedException {

		String pdfImage = "flower.pdf";
		String jpgImage = "Bigpic (3).jpg";
		String xlsxFile = "Invalid.xlsx";
		String xlsFile = "tagging_template.xls";
		String pngImage = "valid.png";
		String jpegImage = "image1.jpeg";
		String largeImage = "Bigpic.jpg";

		TestUtils.testTitle("Upload a file with size greater than 200KB: " + largeImage);
		TestUtils.uploadFile(By.id("userImageUpload"), largeImage);
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p",	"Image is too large. Image size can not be greater than 200kb. Compress image or upload a different image.");
		Thread.sleep(500);
		// Close the Modal
		getDriver().findElement(By.xpath("//div[2]/button/span/span")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Upload invalid file format .pdf: " + pdfImage);
		TestUtils.uploadFile(By.id("userImageUpload"), pdfImage);
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");
		Thread.sleep(500);
		// Close the Modal
		getDriver().findElement(By.xpath("//div[2]/button/span/span")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Upload invalid file format .xls: " + xlsFile);
		TestUtils.uploadFile(By.id("userImageUpload"), xlsFile);
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");
		Thread.sleep(500);
		// Close the Modal
		getDriver().findElement(By.xpath("//div[2]/button/span/span")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Upload invalid file format .xlsx: " + xlsxFile);
		TestUtils.uploadFile(By.id("userImageUpload"), xlsxFile);
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");
		Thread.sleep(500);
		// Close the Modal
		getDriver().findElement(By.xpath("//div[2]/button/span/span")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Upload valid file format .png: " + pngImage);
		TestUtils.uploadFile(By.id("userImageUpload"), pngImage);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);

		TestUtils.testTitle("Upload valid file format .jpeg: " + jpegImage);
		TestUtils.uploadFile(By.id("userImageUpload"), jpegImage);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);

		TestUtils.testTitle("Upload valid file format .jpg: " + jpgImage);
		TestUtils.uploadFile(By.id("userImageUpload"), jpgImage);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);

	}
	
	@Test
	public void frequentTransactionsTest() throws InterruptedException {
		
		TestUtils.testTitle("Navigate to Frequent Transactions");
		TestUtils.testTitle("To confirm that there are 4 categories on Frequent Transactions");
		
		// To confirm that there are 4 categories on Frequent Transactions
		TestUtils.assertSearchText("XPATH", "//div[2]/div/p", "FREQUENT TRANSACTIONS");
		TestUtils.assertSearchText("XPATH", "//div[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/app-dashboard/div[2]/div/mat-card/mat-tab-group/mat-tab-header/div[2]/div/div/div", "GTBank Transfers");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[2]/div", "Other Banks Transfers");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[3]/div", "Bills Payment");
		TestUtils.assertSearchText("XPATH", "//div[4]/div",	"Airtime/Data Purchase");
		Thread.sleep(1000);

	}
	
	@Test
	public void iRequireTest() throws InterruptedException {
		
		TestUtils.testTitle("Navigate to IRequire");
		TestUtils.assertSearchText("ID",	"IRequire", "IRequire");
		TestUtils.scrollToElement("XPATH", "//mat-grid-tile[5]/figure/mat-card");
		
		// To confirm that there are 5 Cards on IRequire
		TestUtils.testTitle("To confirm that there are 5 Cards on IRequire");
		TestUtils.assertSearchText("XPATH",	"//mat-card/p[contains(text(), 'Cash Withdrawal')]", "Cash Withdrawal");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p[contains(text(),'Statement Collection')]", "Statement Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[3]/figure/mat-card/p[contains(text(),'Token Request/Collection')]", "Token Request/Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Card Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]/figure/mat-card/p", "Chequebook Collection");
		Thread.sleep(500);
		
		// Rater
		//TestUtils.raterTest("Dashboard");
	}
	
	
}
