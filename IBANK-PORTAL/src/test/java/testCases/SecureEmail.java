package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.TestBase;
import util.TestUtils;

public class SecureEmail extends TestBase{
	
	@Test
	public void navigateToSecureEmailTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Secure Email");
		
		// Click on Proceed to Internet Banking button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clearLoader")));
		getDriver().findElement(By.id("clearLoader")).click();
		Thread.sleep(500);
		
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[7]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[7]/figure/mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Secure Email')]", "Secure Email");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Create a request using a secured email channel");
		Thread.sleep(500);
		
	}
	
	@Test
	public void secureEmailRequestFormValidationTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		String pdfImage = "flower.pdf";
		String jpgImage = "Bigpic (3).jpg";
		String xlsxFile = "Invalid.xlsx";
		String xlsFile = "tagging_template.xls";
		String pngImage = "valid.png";
		String jpegImage = "image1.jpeg";
		String largeImage = "Bigpic.jpg";
		
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);
		
		// Click on Secure Email
		TestUtils.testTitle("Navigate back to Secure Email view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[7]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[7]/figure/mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Secure Email')]", "Secure Email");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Create a request using a secured email channel");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that no default value for request type is selected in the request type dropdown");
		String reqType = getDriver().findElement(By.xpath("//div[2]/input")).getText();
		if (reqType.isEmpty()) {
			testInfo.get().info("No request type is selected by default");
		} else {
			testInfo.get().error("Request type is selected by default");
		}
		
		TestUtils.testTitle("To confirm that the first account on the account number dropdown is selected by default");
		try {
			String acc = getDriver().findElement(By.xpath("//gtibank-accounts-typeahead/div/ng-select/div")).getAttribute("value");
			if (!acc.isEmpty()) {
				 testInfo.get().log(Status.INFO, "<b> First Account Number: </b>" + acc + " found");
				    Thread.sleep(500);
			} else {
				testInfo.get().error("No Account is selected by default");
			}
		} catch (Exception e) {
			testInfo.get().error("No Account is selected by default");
		}
		
	    TestUtils.testTitle("To confirm email is autopopulated in the email text field");
		String email = getDriver().findElement(By.id("customerEmailAddress")).getAttribute("value");
	    testInfo.get().log(Status.INFO, "<b> Email: </b>" + email + " found");
	    Thread.sleep(500);
	    
	    // Rater
	    TestUtils.raterTest("Secure Email");
	    Thread.sleep(500);
	    
	    TestUtils.testTitle("To confirm that secret answer is sent to the customers registered email when user clicks on the Forgot Secret question hyperlink ");
	    getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Secret Question?')]")).click();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Secret Answer recovery successful. Your Secret Answer has been sent to you email");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	    
	    TestUtils.testTitle("Upload valid file format .jpeg: " + jpegImage);
		TestUtils.uploadFile(By.xpath("//div[2]/div/input"), jpegImage);
		testInfo.get().info("File upload is successful");
		Thread.sleep(1000);

		TestUtils.testTitle("Upload valid file format .pdf: " + pdfImage);
		TestUtils.uploadFile(By.id("userImageUpload"), pdfImage);
		testInfo.get().info("File upload is successful");
		Thread.sleep(1000);
		
		TestUtils.testTitle("Upload valid file format .jpg: " + jpgImage);
		TestUtils.uploadFile(By.id("userImageUpload"), jpgImage);
		testInfo.get().info("File upload is successful");
		Thread.sleep(1000);
		
//		TestUtils.testTitle("Upload a file with size greater than 200KB: " + largeImage);
//		TestUtils.uploadFile(By.id("userImageUpload"), largeImage);
//		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p",	"Image is too large. Image size can not be greater than 200kb. Compress image or upload a different image.");
//		Thread.sleep(500);
//		// Close the Modal
//		getDriver().findElement(By.xpath("//div[2]/button/span/span")).click();
//		Thread.sleep(500);
	
		TestUtils.testTitle("Upload invalid file format .xls: " + xlsFile);
		TestUtils.uploadFile(By.id("userImageUpload"), xlsFile);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);

		TestUtils.testTitle("Upload invalid file format .xlsx: " + xlsxFile);
		TestUtils.uploadFile(By.id("userImageUpload"), xlsxFile);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);

		TestUtils.testTitle("Upload invalid file format .png: " + pngImage);
		TestUtils.uploadFile(By.id("userImageUpload"), pngImage);
		TestUtils.imageAlertSwitch();
		Thread.sleep(1000);
		
	}
	
	 public void name() {
		
	}

}
