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
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Frequent Transactions");
		TestUtils.testTitle("To confirm that there are 4 categories on Frequent Transactions");
		
		// To confirm that there are 4 categories on Frequent Transactions
		TestUtils.assertSearchText("XPATH", "//div[2]/div/p", "FREQUENT TRANSACTIONS");
		TestUtils.assertSearchText("XPATH", "//div[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/app-dashboard/div[2]/div/mat-card/mat-tab-group/mat-tab-header/div[2]/div/div/div", "GTBank Transfers");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[2]/div", "Other Banks Transfers");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[3]/div", "Bills Payment");
		TestUtils.assertSearchText("XPATH", "//div[4]/div",	"Airtime/Data Purchase");
		Thread.sleep(1000);
		
		// To confirm that records are displayed on GTBank Transfers section
//		TestUtils.testTitle("To confirm that records are displayed on GTBank Transfers section");
//		try {
//			testInfo.get().info("<b> Assert total number of records </b>");
//			int recordCount = getDriver().findElements(By.xpath("//*[@id=\"mat-tab-content-1-0\"]/div/frequent-transfers/span/div/div/mat-grid-list/div/mat-grid-tile/figure/mat-card")).size();
//			//int recordCount = getDriver().findElements(By.cssSelector("mat-grid-list.m-3.animated.fadeIn.mat-grid-list")).size();
//			if (TestUtils.isElementPresent("XPATH", "//mat-card/button/span/mat-icon")) {
//				testInfo.get().info("Total number of Records displayed: <b>" + recordCount + "</b>");
//			} else {
//				testInfo.get().error("Table is empty.");
//				TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//				Thread.sleep(500);
//			}
//			
//		} catch (Exception e) {
//			TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//			Thread.sleep(500);
//		}
		
		// To confirm that user is directed to Transfer page when the user clicks on the 'Go to Transfer' button on GTBank Transfer view
		TestUtils.testTitle("To confirm that user is directed to Transfer page when the user clicks on the 'Go to Transfer' button on GTBank Transfer view");
		try {
			getDriver().findElement(By.xpath("//frequent-transfers/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Transfers')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Transfers')]", "Transfers");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Transfer money between your accounts or other accounts.");
			Thread.sleep(500);
			
			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'GTBank Transfers')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'GTBank Transfers')]", "GTBank Transfers");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to GTBank Account Holders");
			Thread.sleep(500);
			
			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		}
		
		
		/*// To confirm that records are displayed on Other Banks Transfers section
		TestUtils.testTitle("To confirm that records are displayed on Other Banks Transfers section");
		
		// Click on Other Banks Transfers
		getDriver().findElement(By.xpath("//div[2]/div/div/div[2]/div")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'No Record - No frequent records')]")));
		TestUtils.assertSearchText("XPATH", "//p[contains(text(),'No Record - No frequent records')]", "No Record - No frequent records");
		Thread.sleep(500);*/
	
		
		// To confirm that user is directed to Transfer page when the user clicks on the 'Go to Transfer' button on Other Banks Transfers view
		TestUtils.testTitle("To confirm that user is directed to Transfer page when the user clicks on the 'Go to Transfer' button on Other Banks Transfers view");
		// Click on Other Banks Transfer
		getDriver().findElement(By.xpath("//div[2]/div/div/div[2]")).click();
		Thread.sleep(1000);
		try {
			getDriver().findElement(By.xpath("//frequent-transfers/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Transfers')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Transfers')]", "Transfers");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Transfer money between your accounts or other accounts.");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Other Banks')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Other Banks')]", "Other Banks");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		}
		
		// To confirm that records are displayed on Bills Payment section
//		TestUtils.testTitle("To confirm that records are displayed on GTBank Transfers section");
//		try {
//			testInfo.get().info("<b> Assert total number of records </b>");
//			int recordCount = getDriver().findElements(By.xpath("//span/div/div")).size();
//			//int recordCount = getDriver().findElements(By.cssSelector("mat-grid-list.m-3.animated.fadeIn.mat-grid-list")).size();
//			if (TestUtils.isElementPresent("XPATH", "//mat-card/button/span/mat-icon")) {
//				testInfo.get().info("Total number of Records displayed: <b>" + recordCount + "</b>");
//			} else {
//				testInfo.get().error("Table is empty.");
//				TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//				Thread.sleep(500);
//			}
//			
//		} catch (Exception e) {
//			TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//			Thread.sleep(500);
//		}
		
		// To confirm that user is directed to Bills and Payments page when the user clicks on the 'Make New Payment' button on Bills Payment view
		TestUtils.testTitle("To confirm that user is directed to Bills and Payments page when the user clicks on the 'Make New Payment' button on Bills Payment view");
		// Click on Bills Payment
		getDriver().findElement(By.xpath("//div[2]/div/div/div[3]")).click();
		Thread.sleep(1000);
		try {
			getDriver().findElement(By.xpath("//gtibank-payment-history-cards/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Payment Categories')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Payment Categories')]", "Payment Categories");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Select a payment category to begin or user the search box to find a specific product.");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Payment Categories')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Payment Categories')]", "Payment Categories");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Select a payment category to begin or user the search box to find a specific product.");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		}
		
		// To confirm that records are displayed on Airtime Purchase section
//		TestUtils.testTitle("To confirm that records are displayed on GTBank Transfers section");
//		try {
//			testInfo.get().info("<b> Assert total number of records </b>");
//			int recordCount = getDriver().findElements(By.xpath("//span/div/div")).size();
//			//int recordCount = getDriver().findElements(By.cssSelector("mat-grid-list.m-3.animated.fadeIn.mat-grid-list")).size();
//			if (TestUtils.isElementPresent("XPATH", "//mat-card/button/span/mat-icon")) {
//				testInfo.get().info("Total number of Records displayed: <b>" + recordCount + "</b>");
//			} else {
//				testInfo.get().error("Table is empty.");
//				TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//				Thread.sleep(500);
//			}
//			
//		} catch (Exception e) {
//			TestUtils.assertSearchText("XPATH", "//frequent-transfers/div/p", "- No transfer record");
//			Thread.sleep(500);
//		}
		
		// To confirm that user is directed to Airtime/Data Topup page when the user clicks on the 'Click for Airtime Topup' button on Airtime Purchase view
		TestUtils.testTitle("To confirm that user is directed to Airtime/Data Topup page when the user clicks on the 'Click for Airtime Topup' button on Airtime Purchase view");
		// Click on Airtime Purchase
		getDriver().findElement(By.xpath("//div[4]")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//app-gtibank-frequent-topup/div/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Airtime/Data Topup')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Airtime/Data Topup')]", "Airtime/Data Topup");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Topup your airtime or data bundle");
		Thread.sleep(500);

		// Click on Dashboard
		getDriver().findElement(By.xpath("//a/span[2]")).click();
		Thread.sleep(1000);
		
		
		// To confirm that records are displayed on Data Purchase section
		// Click on Data Purchase
		getDriver().findElement(By.xpath("//div[4]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/mat-tab-group/mat-tab-header/div[2]/div/div/div[2]")).click();
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that records are displayed on GTBank Transfers section");
		TestUtils.assertSearchText("XPATH", "//app-gtibank-frequent-topup/div/p", "No records found for frequent topup.");
		Thread.sleep(500);
		
		
		// To confirm that user is directed to Airtime/Data Topup page when the user clicks on the 'Click for Data Topup' button on Data Purchase view
		TestUtils.testTitle("To confirm that user is directed to Airtime/Data Topup page when the user clicks on the 'Click for Data Topup' button on Data Purchase view");
		try {
			getDriver().findElement(By.xpath("//app-gtibank-frequent-topup/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Airtime/Data Topup')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Airtime/Data Topup')]", "Airtime/Data Topup");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Topup your airtime or data bundle");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			getDriver().findElement(By.xpath("//figure/mat-card")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'MTN Airtime')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'MTN Airtime')]", "MTN Airtime");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Buy MTN Airtime or Data");
			Thread.sleep(500);

			// Click on Dashboard
			getDriver().findElement(By.xpath("//a/span[2]")).click();
			Thread.sleep(1000);
		}
	}
	
	@Test
	public void iRequireTest() throws InterruptedException {
		
		TestUtils.testTitle("Navigate to IRequire");
		TestUtils.assertSearchText("XPATH",	"//text/tspan[contains(text(),'IRequire')]", "IRequire");
		TestUtils.scrollToElement("XPATH", "//mat-grid-tile[5]/figure/mat-card");
		
		// To confirm that there are 5 Cards on IRequire
		TestUtils.testTitle("To confirm that there are 5 Cards on IRequire");
		TestUtils.assertSearchText("XPATH",	"//figure/mat-card/p", "Cash Withdrawal");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p", "Statement Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[3]/figure/mat-card/p", "Token Request/Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Card Collection");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]/figure/mat-card/p", "Chequebook Collection");
		Thread.sleep(500);
		
		// To confirm that user is directed to Cash Withdrawal view when the Cash Withdrawal card is clicked
		TestUtils.testTitle("To confirm that user is directed to Cash Withdrawal view when the Cash Withdrawal card is clicked");
		getDriver().findElement(By.xpath("//figure/mat-card")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//mat-card/h2", "Cash Withdrawal");
		Thread.sleep(500);
		
		// Click on the Back button
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a/span/span");
		getDriver().findElement(By.xpath("//a/span/span")).click();
		Thread.sleep(1000);
		
		// To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked
		TestUtils.testTitle("To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked");
		getDriver().findElement(By.xpath("//mat-grid-tile[2]/figure/mat-card")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//mat-card/h2", "Statement Collection");
		Thread.sleep(500);

		// Click on the Back button
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a/span/span");
		getDriver().findElement(By.xpath("//a/span/span")).click();
		Thread.sleep(1000);
		
		// To confirm that user is directed to Token Request/Collection view when the Token Request/Collection card is clicked
		TestUtils.testTitle("To confirm that user is directed to Token Request/Collection view when the Token Request/Collection card is clicked");
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card/p")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//mat-card/h2", "Token Request/Collection");
		Thread.sleep(500);

		// Click on the Back button
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a/span/span");
		getDriver().findElement(By.xpath("//a/span/span")).click();
		Thread.sleep(1000);
		
		// To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked
		TestUtils.testTitle("To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked");
		getDriver().findElement(By.xpath("//mat-grid-tile[4]/figure/mat-card/p")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//mat-card/h2", "Card Collection");
		Thread.sleep(500);

		// Click on the Back button
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a/span/span");
		getDriver().findElement(By.xpath("//a/span/span")).click();
		Thread.sleep(1000);
		
		// To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked
		TestUtils.testTitle("To confirm that user is directed to Statement Collection view when the Statement Collection card is clicked");
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//mat-card/h2", "Chequebook Collection");
		Thread.sleep(500);

		// Click on the Back button
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a/span/span");
		getDriver().findElement(By.xpath("//a/span/span")).click();
		Thread.sleep(1000);
		
		// Quick Credit
		TestUtils.testTitle("To confirm that user is directed to the Quick Credit page when the user clicks on the image");
		getDriver().findElement(By.xpath("//mat-card[2]/img")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Quick Credit')]", "Quick Credit");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Get instant Loan");
		Thread.sleep(500);
		
		// Rater
		TestUtils.raterTest("Dashboard");
	}
	
	
}
