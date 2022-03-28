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

public class MTNAirtimeDataTopUp extends TestBase{
	
	@Test
	public static void navigateToMTNAirtimeDataTopUpTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		AirtimeDataTopup.navigateToAirtimeDataTopupTest();
		
		// Click on MTN Airtime/Data Topup
		TestUtils.testTitle("Click on MTN Airtime/Data Topup");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[1]/figure/mat-card")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'MTN Airtime')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'MTN Airtime')]", "MTN Airtime");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Buy MTN Airtime or Data");
		Thread.sleep(500);
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void validPurchaseAirtimeTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("MTNAirtimeDataTopUp");

		String number = (String) envs.get("number");
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String token = (String) envs.get("token");
		String amount = (String) envs.get("amount");
		
		TestUtils.testTitle("Purchase Airtime");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/label[1]")));
		
		// Select Amount
		getDriver().findElement(By.xpath("//div/button/span[contains(text(),'"+ amount+ "')]")).click();
		Thread.sleep(500);
		
		// Enter Phone Number
		getDriver().findElement(By.id("number")).clear();
		getDriver().findElement(By.id("number")).sendKeys(number);
		TestUtils.scrollToElement("ID", "submit");
			
		// Select Account to Debit 
		getDriver().findElement(By.id("account")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Secret Answer
		getDriver().findElement(By.xpath("//input[@type='password']")).clear();
		getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(validSecretAnswer);
		
		// Submit button
		getDriver().findElement(By.id("submit")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("tokenInput")).clear();
		getDriver().findElement(By.id("tokenInput")).sendKeys(token);
		testInfo.get().info("<b> Airtime purchase was successful </b>");
		
		// Click Back button 
		getDriver().findElement(By.xpath("//button[contains(text(),'Back')]")).click();
		Thread.sleep(500);
	}

	@Parameters ("testEnv")
	@Test
	public void validPurchaseDataTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("MTNAirtimeDataTopUp");

		String number = (String) envs.get("number");
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String token = (String) envs.get("token");
		
		TestUtils.testTitle("Purchase Data");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div/div/label[2]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/label[2]")));
		
		// Click on data tab
		getDriver().findElement(By.xpath("//div/div/label[2]")).click();
		Thread.sleep(500);
		
		// Select Data bundle
		getDriver().findElement(By.id("databundle")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/span[contains(text(),'50MB ')]")).click();
		Thread.sleep(500);
		
		// Enter Phone Number
		getDriver().findElement(By.id("number")).clear();
		getDriver().findElement(By.id("number")).sendKeys(number);
		TestUtils.scrollToElement("ID", "submit");
			
		// Select Account to Debit 
		getDriver().findElement(By.id("account")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Secret Answer
		getDriver().findElement(By.xpath("//input[@type='password']")).clear();
		getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(validSecretAnswer);
		
		// Submit button
		getDriver().findElement(By.id("submit")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("tokenInput")).clear();
		getDriver().findElement(By.id("tokenInput")).sendKeys(token);
		testInfo.get().info("<b> Data purchase was successful </b>");
		
		// Click Back button 
		getDriver().findElement(By.xpath("//button[contains(text(),'Back')]")).click();
		Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
	@Test
	public void mtnValidationTest(String testEnv) throws InterruptedException, Exception {
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
		JSONObject envs = (JSONObject) config.get("MTNAirtimeDataTopUp");

		String amount = (String) envs.get("amount");
		
		// To confirm that when user clicks on the 'Menu List' button, user is directed	back to main menu of account module
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of Airtime/Data module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Airtime/Data Topup')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Airtime/Data Topup')]", "Airtime/Data Topup");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Topup your airtime or data bundle");
		Thread.sleep(500);
		
		// Click on MTN Airtime
		TestUtils.testTitle("Navigate back to MTN Airtime view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[1]/figure/mat-card")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'MTN Airtime')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'MTN Airtime')]", "MTN Airtime");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Buy MTN Airtime or Data");
		Thread.sleep(500);
		
		// To confirm that the Amount Keypad populates a value on the Amount field
		TestUtils.testTitle("To confirm that the Amount Keypad populates a value on the Amount field");
		getDriver().findElement(By.xpath("//div/button/span[contains(text(),'" + amount + "')]")).click();
		Thread.sleep(500);
		String amt = getDriver().findElement(By.xpath("//input[@ng-reflect-name = 'airtimeAmount']")).getAttribute("value");
		testInfo.get().info(amt + " found");
	
		// Rater test
		TestUtils.raterTest("MTN Airtime/Data Topup");
		
	}
	
}
