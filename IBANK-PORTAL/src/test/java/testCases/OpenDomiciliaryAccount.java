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

public class OpenDomiciliaryAccount extends TestBase{
	
	@Test
	public void navigateToOpenDomAccountTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Open Domiciliary Account");
		
		// Click on Proceed to Internet Banking button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clearLoader")));
		getDriver().findElement(By.id("clearLoader")).click();
		Thread.sleep(500);
		
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Open Dom Account')]", "Open Dom Account");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Open a new Domiciliary Account");
		Thread.sleep(500);
	}
	
	@Test
	public void openDomAccountValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// To confirm that when user clicks on the 'Menu List' button, user is directed	back to main menu of account module
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);

		// Click on Open Domiciliary Account
		TestUtils.testTitle("Navigate back to Open Domiciliary Account view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Open Dom Account')]", "Open Dom Account");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Open a new Domiciliary Account");
		Thread.sleep(500);
		
		// To confirm that secret answer is sent to user's email when the Forgot Secret Question link is clicked
		TestUtils.testTitle("To confirm that secret answer is sent to user's email when the Forgot Secret Question link is clicked");
		// Click on Forgot Secret Answer link
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Secret Question?')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Secret Answer recovery successful. Your Secret Answer has been sent to you email");
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Recover Secret Answer')]", "Recover Secret Answer");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// To confirm that Terms & Conditions bear the correct customer's name
		TestUtils.testTitle("To confirm that Terms & Conditions bear the correct customer's name");
		String termsText = getDriver().findElement(By.xpath("//div[3]/div/span")).getText();
		TestUtils.assertSearchText("XPATH", "//div[3]/div/span", termsText);
		Thread.sleep(500);
		
		// To confirm that currency dropdown contains Dollars, Pounds and Euros
		TestUtils.testTitle("To confirm that currency dropdown contains Dollars, Pounds and Euros");
		getDriver().findElement(By.xpath("//ng-select/div")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-select/div", "Select Currency");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div", "USD");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "GBP");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "EUR");
		Thread.sleep(500);
		
		// Rater
		TestUtils.raterTest("Open Domiciliary Account");
		
		// Account officer
		TestUtils.accOfficerValidationTest();
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void duplicateDomAccountTest(String testEnv) throws Exception {
		
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
		JSONObject envs = (JSONObject) config.get("OpenDomiciliaryAccount");

		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		
		TestUtils.testTitle("To confirm that user cannot open more than one Dollar account");
		
		// Select Dollar currency
		getDriver().findElement(By.xpath("//ng-select/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(500);
		
		// Enter Secret Answer
		getDriver().findElement(By.xpath("//div[2]/input")).clear();
		getDriver().findElement(By.xpath("//div[2]/input")).sendKeys(validSecretAnswer);
		
		// Click on Terms and Conditions checkbox
		getDriver().findElement(By.xpath("//label")).click();
		Thread.sleep(500);
		
		// Click on Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Open Dom Account");
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "You Already Have a Dollar Domiciliary Account");
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that user cannot open more than one Euro account");
		
		// Select Euro currency
		//getDriver().findElement(By.className("ng-clear-wrapper ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-select/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[3]")).click();
		Thread.sleep(500);
		
		// Enter Secret Answer
		getDriver().findElement(By.xpath("//div[2]/input")).clear();
		getDriver().findElement(By.xpath("//div[2]/input")).sendKeys(validSecretAnswer);
		
		// Click on Terms and Conditions checkbox
		getDriver().findElement(By.xpath("//label")).click();
		Thread.sleep(500);
		
		// Click on Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Open Dom Account");
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "You Already Have a Euros Domiciliary Account");
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that user cannot open more than one Pounds account");
		
		// Select Pounds currency
		//getDriver().findElement(By.className("ng-clear-wrapper ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-select/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		// Enter Secret Answer
		getDriver().findElement(By.xpath("//div[2]/input")).clear();
		getDriver().findElement(By.xpath("//div[2]/input")).sendKeys(validSecretAnswer);
		
		// Click on Terms and Conditions checkbox
		getDriver().findElement(By.xpath("//label")).click();
		Thread.sleep(500);
		
		// Click on Submit button
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Open Dom Account");
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "You Already Have a Pounds Domiciliary Account");
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
	
	}
}
