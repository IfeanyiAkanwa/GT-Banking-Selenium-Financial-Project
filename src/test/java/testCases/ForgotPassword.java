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

import com.aventstack.extentreports.Status;

import util.TestBase;
import util.TestUtils;

public class ForgotPassword extends TestBase {

	@Test
	public void navigateToForgotPasswordTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.getStartedPage();
		
		TestUtils.testTitle("Navigate to Forgot password");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Forgot Password?')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Password?')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		TestUtils.assertSearchText("XPATH", "//div/gtibank-forgot/form/h1", "Recover Password");
		Thread.sleep(500);
	}
	
	public void doForgotPasswordTest(String userName, String secretAnswer) throws InterruptedException {
		
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys(userName);
	    getDriver().findElement(By.id("secretAnswer")).clear();
	    getDriver().findElement(By.id("secretAnswer")).sendKeys(secretAnswer);
	    
	    // Submit button
	    TestUtils.clickElement("XPATH", "//button[@type='submit']");
	    Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
	@Test
	public void forgotPasswordValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("ForgotPassword");

		String alphanumericNum = (String) envs.get("alphanumericNum");
		String specialCharnum = (String) envs.get("specialCharnum");
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String validUsername = (String) envs.get("validUsername");
		String invalidSecretAnswer = (String) envs.get("invalidSecretAnswer");
		String invalidUsername = (String) envs.get("invalidUsername");
		
		// Recover password with empty username and empty secret answer
		TestUtils.testTitle("Recover password with empty username: ( ) and empty secret answer: ( )");
		doForgotPasswordTest(" " , " 5");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserId is required");
		TestUtils.clickElement("XPATH", "//mat-card/div/button/span/mat-icon");
		//getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover password with username containing special characters and valid secret answer
		TestUtils.testTitle("Recover password with username containing special characters: (" + specialCharnum + ") and valid secret answer: (" + validSecretAnswer + ")");
		doForgotPasswordTest(specialCharnum, validSecretAnswer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover password with alphanumeric username and valid secret answer
		TestUtils.testTitle("Recover password with alphanumeric username: (" + alphanumericNum + ") and valid secret answer: (" + validSecretAnswer + ")");
		doForgotPasswordTest(alphanumericNum, validSecretAnswer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover password with empty username and valid secret answer
		TestUtils.testTitle("Recover password with empty username: ( ) and valid secret answer: (" + validSecretAnswer + ")");
		doForgotPasswordTest(" " , validSecretAnswer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserId is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover password with valid username and empty secret answer
		TestUtils.testTitle("Recover password with valid username: (" + validUsername + ") and empty secret answer: ( )");
		doForgotPasswordTest(validUsername , " ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- Invalid Secret Answer.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Recover password with invalid username and invalid secret answer
		TestUtils.testTitle("Recover password with invalid username: (" + invalidUsername + ") and invalid secret answer: (" + invalidSecretAnswer + ")");
		doForgotPasswordTest(invalidUsername , invalidSecretAnswer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "- UserId incorrect.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Masked Password
		TestUtils.testTitle("To confirm that Secret Answer is visible when user clicks on the eye Icon");
		getDriver().findElement(By.id("secretAnswer")).clear();
		getDriver().findElement(By.id("secretAnswer")).sendKeys(validSecretAnswer);
		Thread.sleep(500);

		// Click on the eye icon
		getDriver().findElement(By.xpath("//span/mat-icon")).click();
		Thread.sleep(1000);

		String ans = getDriver().findElement(By.id("secretAnswer")).getAttribute("value");
	    testInfo.get().log(Status.INFO, "<b> Secret Answer: </b>" + ans+ " found");
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void forgotPasswordLinksValidationTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		    
		// Forgot Secret Answer 
		TestUtils.testTitle("To confirm that user is directed to the Forgot Secret Answer page from Forgot Password page");
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Secret Answer?')]")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h1", "Recover Secret Answer");
		Thread.sleep(500);
		
		//Navigate back to Forgot Password
		TestUtils.testTitle("To confirm that user is directed to the Forgot Password page from Forgot Secret Answer page");
		testInfo.get().info("<b> Click on Login hyperlink </b>");
		getDriver().findElement(By.linkText("Login.")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Login");
		navigateToForgotPasswordTest();
		
		// GTBank.com
		TestUtils.testTitle("To confirm that user is directed to the GTBank.com website from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'GTBank.com')]"), "Guaranty Trust Bank Plc | GTBank"); 
		Thread.sleep(500);

		// Terms and Conditions
		TestUtils.testTitle("To confirm that user is directed to the Terms and Conditions page from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'Terms and Conditions')]"), "Terms and Conditions | GTBank");
		Thread.sleep(500);
		
		// Privacy Policy
		TestUtils.testTitle("To confirm that user is directed to the Privacy Policy page from Login page");
		TestUtils.switchToNewTab(By.xpath("//a[contains(text(),'Privacy Policy')]"), "Privacy Policy | GTBank");
		Thread.sleep(1000);
		
		// Login Here
		TestUtils.testTitle("To confirm that user is directed to the Login page from Forgot Password page");
		getDriver().findElement(By.xpath("//a[contains(text(),'Login here')]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Login");
		Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
	@Test
	public void validForgotPasswordTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("ForgotPassword");
		
		String validSecretAnswer = (String) envs.get("validSecretAnswer");
		String validUsername = (String) envs.get("validUsername");
		
		TestUtils.testTitle("Recover password with valid username: (" + validUsername + ") and valid secret answer: (" + validSecretAnswer + ")");
		doForgotPasswordTest(validUsername, validSecretAnswer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Recover Password')]", "Recover Password");
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Password recovery successful. Your password has been sent to you email");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	}

}
