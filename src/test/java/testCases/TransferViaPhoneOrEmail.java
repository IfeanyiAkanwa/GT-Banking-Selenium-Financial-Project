package testCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class TransferViaPhoneOrEmail extends TestBase{

	
	@Test
	public void navigateToTransferViaPhoneOrEmailTest() throws InterruptedException {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		

		
		TestUtils.testTitle("Navigate to Transfer To GTBank");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
		getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("Navigate To Transfer Via Phone/Email");
		
		//CLick on Transfer Via Phone/Email
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[4]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[4]/figure/mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Via Phone or Email')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Via Phone or Email')]", "Via Phone or Email");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Quickly search for beneficiary accounts using phone or email");
		Thread.sleep(500);		

	}
	
	@Test
public void transferValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
		Transfers.clickOnMenuList1();
				
				
				//Click on Transfer Via Phone/Email again
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[4]/figure/mat-card/p")));
				getDriver().findElement(By.xpath("//mat-grid-tile[4]/figure/mat-card/p")).click();
				Thread.sleep(500);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Via Phone or Email')]")));
				TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Via Phone or Email')]", "Via Phone or Email");
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Quickly search for beneficiary accounts using phone or email");
				Thread.sleep(500);	
				
				
				
				Transfers.frequentTransfers();
				TestUtils.raterTest("Transfer Via Phone/Email");
	}
	@Parameters ("testEnv")
	@Test
	public void transferViaPhoneNumberTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("TransferViaPhoneNumber");

		String phoneNumber = (String) envs.get("phoneNumber");
		String amount = (String) envs.get("amount");
		String Remark = (String) envs.get("Remark");		
		String token = (String) envs.get("token");
		
		
		////button[@id='mat-button-toggle-36-button']/div
		TestUtils.testTitle("Navigate to Phone Number");
		TestUtils.assertSearchText("XPATH", "//mat-button-toggle[@value='1']", "phone Phone number");
		getDriver().findElement(By.xpath("//mat-button-toggle[@value='1']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customerContact")));
		
		
		//Enter Phone Number
		
		getDriver().findElement(By.id("customerContact")).clear();
		getDriver().findElement(By.id("customerContact")).sendKeys(phoneNumber);
		
		//Confirm that the Beneficiary Name, Account Number, Bank Name and Save after Transfer are displayed.
		
		Assertion.validatePhoneNumberAndEmail();
		Assertion.clickOnSaveBeneficiaryAfterTransfer();
		
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Amount
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
				
				
		TestUtils.testTitle("Confirm that the charge for the transaction is diplayed.");
		String chgInfo = getDriver().findElement(By.xpath("//gtibank-charge-info/p")).getText();
		TestUtils.assertSearchText("XPATH", "//gtibank-charge-info/p",chgInfo);
		
				//Enter Remarks. 
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
		TestUtils.scrollToElement("ID", "transfer");
				
				// Click on Continue
		getDriver().findElement(By.id("transfer")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
				
				// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		/*
		 * //When User inputs correct token (to be used during regression testing)
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		 * TestUtils.assertSearchText("XPATH", "//h3", "Operation Successful");
		 */
		
		
		//Click on Submit
		
		getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
    	
    	String msg = getDriver().findElement(By.xpath("//h3")).getText();
    	String msg2 = getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div/p[2]")).getText();
    	
    	testInfo.get().info(msg + "\n" + msg2);
    	
    	
		// Click Back button 
		getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button")).click();
		Thread.sleep(500);
		
		
				
	}
	
	@Parameters ("testEnv")
	@Test
	
	public void transferViaEmailTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("TransferViaEmail");

		String email = (String) envs.get("email");
		String amount = (String) envs.get("amount");
		String Remark = (String) envs.get("Remark");		
		String token = (String) envs.get("token");
		
		
		TestUtils.testTitle("Navigate to Email");
		
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Via Phone or Email')]");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Via Phone or Email')]");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Via Phone or Email')]");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Via Phone or Email')]");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//mat-button-toggle[@value='2']", "email Email address");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-button-toggle[@value='2']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customerContact")));
		
		
		//Enter Email Address
		
		getDriver().findElement(By.id("customerContact")).clear();
		getDriver().findElement(By.id("customerContact")).sendKeys(email);
		
		//Confirm that the Beneficiary Name, Account Number, Bank Name and Save after Transfer are displayed.
		
		
		Assertion.validatePhoneNumberAndEmail();
		Assertion.clickOnSaveBeneficiaryAfterTransfer();
		
		//select account to debit
			getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Amount
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
				
				
		TestUtils.testTitle("Confirm that the charge for the transaction is diplayed.");
		String chgInfo = getDriver().findElement(By.xpath("//gtibank-charge-info/p")).getText();
		TestUtils.assertSearchText("XPATH", "//gtibank-charge-info/p",chgInfo);
		
				//Enter Remarks. 
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
		TestUtils.scrollToElement("ID", "transfer");
				
				// Click on Continue
		getDriver().findElement(By.id("transfer")).click();
		Thread.sleep(500);
		
		//Display token page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		String transDetails = getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div/div/p")).getText();
		TestUtils.assertSearchText("XPATH", "//app-token-confirmation-modal/div/div/div/div/p", transDetails);		
		
		
				// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		
		//Click on Submit
		
		getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
    	
    	String msg = getDriver().findElement(By.xpath("//h3")).getText();
    	String msg2 = getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div/p[2]")).getText();
    	
    	testInfo.get().info(msg + "\n" + msg2);
		
				
		// Click Back button 
		getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button")).click();
		Thread.sleep(500);
				
		
		
	}


}
