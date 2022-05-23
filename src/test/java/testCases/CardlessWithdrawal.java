package testCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import util.TestBase;
import util.TestUtils;

public class CardlessWithdrawal extends TestBase{

	@Test
	public void navigateToCardlessWithdrawal() throws InterruptedException {
		
WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		

		
		TestUtils.testTitle("Navigate to Transfer To GTBank");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
		getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("Navigate To Cardless Withdrawal");
		
		//CLick on Cardless Withdrawal
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")));
				getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")).click();
				Thread.sleep(500);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Cardless Withdrawal')]")));
				TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Cardless Withdrawal')]", "Cardless Withdrawal");
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
				Thread.sleep(500);		
	}
	
	@Test
public void transferValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
				
		
		Transfers.clickOnMenuList1();
		//Click on Cardless Withdrawal again
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")));
				getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")).click();
				Thread.sleep(500);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Cardless Withdrawal')]")));
				TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Cardless Withdrawal')]", "Cardless Withdrawal");
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
				Thread.sleep(500);		
				
				String textInfo = getDriver().findElement(By.xpath("//app-cardless-withdrawal/div/div/div/form/div/div/p")).getText();
				TestUtils.assertSearchText("XPATH", "//app-cardless-withdrawal/div/div/div/form/div/div/p", textInfo);
				
				Transfers.frequentTransfers();
				TestUtils.raterTest("Cardless Withdrawal");
	}

	
	@Parameters ("testEnv")
	@Test
	public void validCardlessWithdrawalTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("CardlessWithdrawal");

		String PhoneNumber = (String) envs.get("PhoneNumber");
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String token = (String) envs.get("token");
		String amount = (String) envs.get("amount");
		
		/*
		 * TestUtils.testTitle("Purchase Airtime");
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//div/div/label[1]")));
		 */
		
		
		//Enter amount
		getDriver().findElement(By.id("cashOutAmt")).clear();
		getDriver().findElement(By.id("cashOutAmt")).sendKeys(amount);
		
		//Select Account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/p[contains(text(), '0537265944')]")).click();
		Thread.sleep(500);
		
		//Enter Phone Number 
		getDriver().findElement(By.id("beneficiaryPhoneNumber")).clear();
		getDriver().findElement(By.id("beneficiaryPhoneNumber")).sendKeys(PhoneNumber);
		
		//Enter Secret Answer
		getDriver().findElement(By.xpath("//input[@type='password']")).clear();
		getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(validSecretAnswer);
				
		String SecretQuestion = getDriver().findElement(By.xpath("//get-secret-question/span/a")).getText();
		TestUtils.assertSearchText("XPATH", "//get-secret-question/span/a", SecretQuestion);
		Thread.sleep(500);
		
		String chgInfo = getDriver().findElement(By.xpath("//gtibank-charge-info/p")).getText();
		TestUtils.assertSearchText("XPATH", "//gtibank-charge-info/p", chgInfo);
		Thread.sleep(500);
		
		//Click on Continue
		
		getDriver().findElement(By.id("submit")).click();
		Thread.sleep(500);
		
		//Display token page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Token Confirmation");
		String transDetails = getDriver().findElement(By.id("tokenHelp")).getText();
		TestUtils.assertSearchText("ID", "tokenHelp", transDetails);
		
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		//Click on Submit
		
		getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
    	
    	String msg = getDriver().findElement(By.xpath("//h3")).getText();
    	String msg2 = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div/div/div")).getText();
    	
    	testInfo.get().info(msg + "\n" + msg2);
    	
    	
    	TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cardless Withdrawal')]");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cardless Withdrawal')]");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cardless Withdrawal')]");
		Thread.sleep(500);
		
			
		getDriver().findElement(By.xpath("//ibank-notifications/div/div/div[2]/button")).click();
		Thread.sleep(500);
				
				
		/*
		 * getDriver().findElement(By.xpath("//get-secret-question/span/a")).click();
		 * TestUtils.AssertAlertMessage("");
		 */
		
}
	@Parameters ("testEnv")
	@Test
	
	
	public void invalidCardlessWithdrawalTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("InvalidCardlessWithdrawal");

		String PhoneNumber = (String) envs.get("PhoneNumber");
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String token = (String) envs.get("token");
		String amount = (String) envs.get("amount");
		
		/*
		 * TestUtils.testTitle("Purchase Airtime");
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//div/div/label[1]")));
		 */
		
		//Enter amount
		getDriver().findElement(By.id("cashOutAmt")).clear();
		getDriver().findElement(By.id("cashOutAmt")).sendKeys(amount);
		
		String displayedError = getDriver().findElement(By.xpath("//div/form/div/div/div/small")).getText();
		TestUtils.assertSearchText("XPATH", "//div/form/div/div/div/small", displayedError);
		Thread.sleep(500);
		
		//Select Account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		//Enter Phone Number 
		getDriver().findElement(By.id("beneficiaryPhoneNumber")).clear();
		getDriver().findElement(By.id("beneficiaryPhoneNumber")).sendKeys(PhoneNumber);
		
		//Enter Secret Answer
		getDriver().findElement(By.xpath("//input[@type='password']")).clear();
		getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(validSecretAnswer);
				
		String SecretQuestion = getDriver().findElement(By.xpath("//get-secret-question/span/a")).getText();
		TestUtils.assertSearchText("XPATH", "//get-secret-question/span/a", SecretQuestion);
		Thread.sleep(500);
		
		String chgInfo = getDriver().findElement(By.xpath("//gtibank-charge-info/p")).getText();
		TestUtils.assertSearchText("XPATH", "//gtibank-charge-info/p", chgInfo);
		Thread.sleep(500);
		
		//Click on Continue
		
		getDriver().findElement(By.id("submit")).click();
		Thread.sleep(500);
		
		
		//Display error message 
		
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Invalid Amount entered");
		String transDetails = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div/div/div")).getText();
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div", transDetails);
		
				
		/*
		 * // Click Close button
		 * 
		 * getDriver().findElement(By.xpath(
		 * "//ibank-notifications/div/div/div[2]/button")).click(); Thread.sleep(500);
		 */
				
		/*
		 * getDriver().findElement(By.xpath("//get-secret-question/span/a")).click();
		 * TestUtils.AssertAlertMessage("");
		 */
		
}
}
