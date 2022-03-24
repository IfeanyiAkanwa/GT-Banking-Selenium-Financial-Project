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

import util.TestBase;
import util.TestUtils;

public class OpenAdditionalAcount extends TestBase{
	
	@Test
	public void navigateToOpenAdditionalAccountTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Open Additional Account");
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(1000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h2/a", "Add Additional Account");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Add an additional account to your profile");
		Thread.sleep(500);
	}

	@Parameters ("testEnv")
	@Test
	public void openAdditionalAccountValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("OpenAdditionalAccount");

		String invalidSecretAnswer = (String) envs.get("invalidSecretAnswer");
		
		// To confirm that the Select Account Type dropdown contains two options "Savings account and current account" 
		TestUtils.testTitle("To confirm that the Select Account Type dropdown contains two options 'Savings account and current account'");
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div[2]/div/span", "Savings Account");
		TestUtils.assertSearchText("XPATH", "//div[2]/div[2]/span", "Current Account");
		Thread.sleep(500);
		
		// To confirm that the secret answer is sent to user's email when user clicks on 'Forgot Secret Question?'
		TestUtils.testTitle("To confirm that the secret answer is sent to user's email when user clicks on 'Forgot Secret Question?'");
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Secret Question?')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p",	"Secret Answer recovery successful. Your Secret Answer has been sent to you email");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/a")));
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);

		// Click on Open Additional Account
		TestUtils.testTitle("Navigate back to Open Additional Account view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Add Additional Account')]", "Add Additional Account");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Add an additional account to your profile");
		Thread.sleep(500);
		
		// To confirm that an error message is displayed when user submits with a wrong secret answer
		TestUtils.testTitle("To confirm that an error message is displayed when user enters a wrong secret answer: " + invalidSecretAnswer);
		
		// Select Account Type
		getDriver().findElement(By.id("accountType")).click();
		TestUtils.clickElement("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]");
		Thread.sleep(500);
		
		// Enter Invalid Secret answer
		getDriver().findElement(By.id("secretAnswer")).clear();
		getDriver().findElement(By.id("secretAnswer")).sendKeys(invalidSecretAnswer);
		
		// Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Access Denied: undefined. Please try again");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//mat-card/div/button/span/mat-icon");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Rater
		TestUtils.raterTest("Open Additional Account");

		// Account officer
		TestUtils.accOfficerValidationTest();
		
		// Navigate to Open Additional Account
		navigateToOpenAdditionalAccountTest();
	}
	
	@Parameters ("testEnv")
	@Test
	public void createAdditionalAccountTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("OpenAdditionalAccount");

		String validSecretAnswer = (String) envs.get("validSecretAnswer");
	
		TestUtils.testTitle("To confirm that user is able to create a current additional sub-account with valid secret answer: " + validSecretAnswer);
		
		// Select Account Type
		getDriver().findElement(By.id("accountType")).click();
		TestUtils.clickElement("XPATH", "//ng-dropdown-panel/div/div/div[2]");
		Thread.sleep(500);
		
		// Enter valid Secret answer
		getDriver().findElement(By.id("secretAnswer")).clear();
		getDriver().findElement(By.id("secretAnswer")).sendKeys(validSecretAnswer);
		
		// Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Success - Additional Account has been Opened");
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Success - Additional Account Has Been Opened");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//mat-card/div/button/span/mat-icon");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that user is able to create a savings additional sub-account with valid secret answer: " + validSecretAnswer);
		
		// Select Account Type
		getDriver().findElement(By.id("accountType")).click();
		TestUtils.clickElement("XPATH", "//ng-dropdown-panel/div/div/div[2]");
		Thread.sleep(500);
		
		// Enter valid Secret answer
		getDriver().findElement(By.id("secretAnswer")).clear();
		getDriver().findElement(By.id("secretAnswer")).sendKeys(validSecretAnswer);

		// Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Success - Additional Account has been Opened");
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Success - Additional Account Has Been Opened");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//mat-card/div/button/span/mat-icon");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-card/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		
	}
	
}
