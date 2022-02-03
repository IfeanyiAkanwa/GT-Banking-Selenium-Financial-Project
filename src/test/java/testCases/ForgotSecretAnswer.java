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

public class ForgotSecretAnswer extends TestBase{

	
	@Test
	public void navigateToForgotSecretAnswerPageTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.getStartedPage();
		
		TestUtils.testTitle("Navigate to Forgot Secret Answer");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Forgot Password?')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Password?')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Forgot Secret Answer?')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Secret Answer?')]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Recover Secret Answer");
		Thread.sleep(500);
	}
	
	public void doForgotSecretAnswerTest(String userName) throws InterruptedException {
		
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys(userName);
	    
	    // Submit button
	    TestUtils.clickElement("XPATH", "//button[@type='submit']");
	    Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
	@Test
	public void forgotSecretAnswerValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("ForgotSecretAnswer");

		String alphanumericNum = (String) envs.get("alphanumericNum");
		String specialCharnum = (String) envs.get("specialCharnum");
		String invalidUsername = (String) envs.get("invalidUsername");
		
		// Recover secret answer with empty username 
		TestUtils.testTitle("Recover Secret Answer with empty username: ( )");
		doForgotSecretAnswerTest(" ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserId is required");
		TestUtils.clickElement("XPATH", "//mat-card/div/button/span/mat-icon");
		Thread.sleep(500);
		
		// Recover secret answer with username containing special characters
		TestUtils.testTitle("Recover secret answer with username containing special characters: " + specialCharnum);
		doForgotSecretAnswerTest(specialCharnum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover secret answer with alphanumeric username 
		TestUtils.testTitle("Recover secret answer with alphanumeric username: " + alphanumericNum);
		doForgotSecretAnswerTest(alphanumericNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover secret answer with invalid username
		TestUtils.testTitle("Recover secret answer with invalid username: " + invalidUsername);
		doForgotSecretAnswerTest(invalidUsername);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void forgotSecretAnswerLinksValidationTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// GTBank.com
		TestUtils.testTitle("To confirm that user is directed to the GTBank.com website from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'GTBank.com')]"), "Guaranty Trust Bank | GTBank"); 
		Thread.sleep(500);

		// Terms and Conditions
		TestUtils.testTitle("To confirm that user is directed to the Terms and Conditions page from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'Terms and Conditions')]"), "Terms and Conditions | GTBank");
		Thread.sleep(500);
		
		// Privacy Policy
		TestUtils.testTitle("To confirm that user is directed to the Privacy Policy page from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'Privacy Policy')]"), "Privacy Policy | GTBank");
		Thread.sleep(1000);
		
		// Login here
		TestUtils.testTitle("To confirm that user is directed to the Login page from Forgot Secret Answer page");
		getDriver().findElement(By.linkText("Login.")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Login");
			
	}
	
	@Parameters ("testEnv")
	@Test
	public void validForgotSecretAnswerTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("ForgotSecretAnswer");

		String validUsername = (String) envs.get("validUsername");
		
		TestUtils.testTitle("Recover password with valid username: " + validUsername);
		doForgotSecretAnswerTest(validUsername);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Recover Secret Answer')]", "Recover Secret Answer");
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Secret Answer recovery successful. Your Secret Answer has been sent to you email");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	}
}
