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

import util.TestBase;
import util.TestUtils;

public class TransferToOwnAccount extends TestBase{
	@Test
	public void navigateToTransferToOwnAccount() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		

		
		TestUtils.testTitle("Navigate to Transfer To GTBank");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
		getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("Navigate To Own Account Transfer");
		
		//CLick on Own Account Transfers
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Own Account')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Own Account')]", "Own Account");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
		Thread.sleep(500);		
	}

@Test
	
	public static void transferValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
		Transfers.clickOnMenuList1();
		
				
				//Click on Transfer to Own Account again
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]/figure/mat-card/p")));
				getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card/p")).click();
				Thread.sleep(500);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Own Account')]")));
				TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Own Account')]", "Own Account");
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
				Thread.sleep(500);	
				
				Transfers.frequentTransfers();
	}


@Parameters ("testEnv")
@Test

	public void ownAccountTransferUnfundedAcctTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
				JSONObject envs = (JSONObject) config.get("OwnAccountTransferUnfunded");

	
					String amount = (String) envs.get("amount");
					String Remark = (String) envs.get("Remark");
					
				//Select Account to Debit
					TestUtils.testTitle("Customer Selects an unfunded Account as Account to Debit");
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToDebit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToDebit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToDebit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//div/p[contains(text(), '0613624193')]")).click();
					Thread.sleep(500);
					
					
					//Select Account To Credit
					
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToCredit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToCredit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToCredit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//div/p[contains(text(), '0004240467')]")).click();
					Thread.sleep(500);
					
					//Enter Amount 
					getDriver().findElement(By.id("transAmt")).clear();
					getDriver().findElement(By.id("transAmt")).sendKeys(amount);
					
					//Enter Remark
					getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
					getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
					
					//Click on Continue to proceed
					getDriver().findElement(By.id("own-transfer")).click();
					Thread.sleep(500);
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-own-account/ibank-notifications/div/div")));
					TestUtils.assertSearchText("XPATH", "//h3", "Transfer Failed");
					TestUtils.assertSearchTextHasValue("XPATH", "//ibank-notifications/div/div/div/div/div/p", "Insufficient Balance.");
					
					
					
					
					//Close page
					//
					
					TestUtils.testTitle("Confirm that customer is taken back to the own transfer page when customer clicks close");
					TestUtils.assertSearchText("XPATH", "//gtibank-own-account/ibank-notifications/div/div/div[2]/button", "Close");
					getDriver().findElement(By.xpath("//gtibank-own-account/ibank-notifications/div/div/div[2]/button")).click();
					//if(getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[contains(text(), 'SAVINGS ACCOUNT')]")).click())
				
					
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Own Account')]")));
					TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Own Account')]", "Own Account");
					TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
					Thread.sleep(500);	
					
					TestUtils.raterTest("Transfer to Own Account");
	}

@Parameters ("testEnv")
@Test

	public void ownAccountTransferFromFundedAcct(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
				JSONObject envs = (JSONObject) config.get("OwnAccountTransfer");

	
					String amount = (String) envs.get("amount");
					String Remark = (String) envs.get("Remark");
					
				//Select Account to Debit
					TestUtils.testTitle("Customer Selects  Account to Debit");
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToDebit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//div/p[contains(text(), 'SAVINGS ACCOUNT')]")).click();
					Thread.sleep(500);
					
					
					
					//Select Account To Credit
					
					getDriver().findElement(By.xpath("//ng-select[@id='AcctToCredit']/div")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//div/p[contains(text(), 'GT SPEND2SAVE')]")).click();
					Thread.sleep(500);
					
					//Enter Amount 
					getDriver().findElement(By.id("transAmt")).clear();
					getDriver().findElement(By.id("transAmt")).sendKeys(amount);
					
					//Enter Remark
					getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
					getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
					
					//Click on Continue to proceed
					getDriver().findElement(By.id("own-transfer")).click();
					Thread.sleep(500);
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-own-account/ibank-notifications/div/div")));
					String transDetails = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div/div/div/p")).getText();
					TestUtils.assertSearchText("XPATH", "//h3", "Successful" );
					TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", transDetails);
					
					
					/*
					 * //Print Receipt TestUtils. TestUtils.
					 * testTitle("Confirm that receipt is downloaded when customer prints receipt");
					 * TestUtils.assertSearchText("XPATH", "//gtibank-generate-receipt/div/button",
					 * "Print Receipt");
					 * getDriver().findElement(By.xpath("//gtibank-generate-receipt/div/button")).
					 * click();
					 * 
					 * testInfo.get().info("Receipt is printed");
					 */
					 
					
					
					
					/*
					//Close page
					
					
					TestUtils.testTitle("Confirm that customer is taken back to the own transfer page when customer clicks close");
					TestUtils.assertSearchText("XPATH", "//gtibank-own-account/ibank-notifications/div/div/div[2]/button", "Close");
					getDriver().findElement(By.xpath("//gtibank-own-account/ibank-notifications/div/div/div[2]/button")).click();
					//if(getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[contains(text(), 'SAVINGS ACCOUNT')]")).click())
				
					
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Own Account')]");
					Thread.sleep(500);
					
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Own Account')]")));
					TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Own Account')]", "Own Account");
					TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
					Thread.sleep(500);	
					
					TransferToOwnAccount.transferValidationTest();*/
					
					//TestUtils.raterTest("Transfer To Own Account");
	}


	
}
