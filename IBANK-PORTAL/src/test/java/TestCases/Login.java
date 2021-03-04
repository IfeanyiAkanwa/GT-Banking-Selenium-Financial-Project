package TestCases;

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

public class Login extends TestBase{

	@Parameters ("testEnv")
	public void loginTest(String testEnv, String userName) throws Exception {
		
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys(userName);
	    
	    // Enter Password
	    getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'1')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'2')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'3')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'4')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'5')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'6')]")).click();
	    Thread.sleep(500);
	    
	    // Submit button
	    getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	    Thread.sleep(500);
	    
	}
	
	@Parameters ("testEnv")
	@Test
	public void accountNumberLoginValidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("Login");

		String accNumLessThan10Digits = (String) envs.get("accNumLessThan10Digits");
		String pw = (String) envs.get("pw");
		String accNumGreaterThan10Digits = (String) envs.get("accNumGreaterThan10Digits");
		String alphanumericAccNum = (String) envs.get("alphanumericAccNum");
		String specialCharAccnum = (String) envs.get("specialCharAccnum");
		String validAccNum = (String) envs.get("validAccNum");
		String invalidPw = (String) envs.get("invalidPw");
		
		TestUtils.getStartedPage();
		
		// Login with Account number less than 10 digits and valid password 
		TestUtils.testTitle("Login with Account number less than 10 digits: (" + accNumLessThan10Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, accNumLessThan10Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with Account number greater than 10 digits and valid password
		TestUtils.testTitle("Login with Account number greater than 10 digits: (" + accNumGreaterThan10Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, accNumGreaterThan10Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);

		// Login with alphanumeric Account number and valid password
		TestUtils.testTitle("Login with Alphanumeric Account number: (" + alphanumericAccNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, alphanumericAccNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with Account number containing Special characters and valid password
		TestUtils.testTitle("Login with Account number containing Special characters: (" + specialCharAccnum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, specialCharAccnum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with empty Account number and valid password
		TestUtils.testTitle("Login with empty Account number: (  ) and valid password: (" + pw + ")");
		loginTest(testEnv, "  ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "Bad Request: UserName is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with valid Account number and empty password
		TestUtils.testTitle("Login with valid Account number: (" + validAccNum + ") and empty password: (  )");
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys(validAccNum);
	    getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
	    // Submit button
	    getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "Bad Request: Password is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with valid Account number and invalid password
		TestUtils.testTitle("Login with valid Account number: (" + validAccNum + ") and invalid password: ( " + invalidPw + " )");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validAccNum);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		 
		// Enter Password
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'1')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'2')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'5')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'6')]")).click();
	    Thread.sleep(500);
		
		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void validAccountNumberLoginTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validAccNum = (String) envs.get("validAccNum");
		
		// Login with valid Account number and valid password
		TestUtils.testTitle("Login with valid Account number : (" + validAccNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, validAccNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-h3.f-w-700.mb-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-h3.f-w-700.mb-0", "Login Successful");
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Hello OYEYINKA, Quickly start up using the frequent transactions section on the dashboard.')]", "Hello OYEYINKA, Quickly start up using the frequent transactions section on the dashboard.");
		Thread.sleep(500);
	}
	
}
