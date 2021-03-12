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
	    TestUtils.clickElement("XPATH", "//button[@type='submit']");
	    Thread.sleep(500);
	    
	}
	
	@Parameters ("testEnv")
	@Test
	public void accountNumberLoginValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String accNumLessThan10Digits = (String) envs.get("accNumLessThan10Digits");
		String pw = (String) envs.get("pw");
		String accNumGreaterThan10Digits = (String) envs.get("accNumGreaterThan10Digits");
		String alphanumericNum = (String) envs.get("alphanumericNum");
		String specialCharnum = (String) envs.get("specialCharnum");
		String validAccNum = (String) envs.get("validAccNum");
		String invalidPw = (String) envs.get("invalidPw");
		
		TestUtils.getStartedPage();
		
		// Login with Account number less than 10 digits and valid password 
		TestUtils.testTitle("Login with Account number less than 10 digits: (" + accNumLessThan10Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, accNumLessThan10Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with Account number greater than 10 digits and valid password
		TestUtils.testTitle("Login with Account number greater than 10 digits: (" + accNumGreaterThan10Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, accNumGreaterThan10Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);

		// Login with alphanumeric Account number and valid password
		TestUtils.testTitle("Login with Alphanumeric Account number: (" + alphanumericNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, alphanumericNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with Account number containing Special characters and valid password
		TestUtils.testTitle("Login with Account number containing Special characters: (" + specialCharnum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, specialCharnum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with empty Account number and valid password
		TestUtils.testTitle("Login with empty Account number: (  ) and valid password: (" + pw + ")");
		loginTest(testEnv, "  ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserName is required");
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: Password is required");
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validAccNum = (String) envs.get("validAccNum");
		
		TestUtils.getStartedPage();
		
		// Login with valid Account number and valid password
		TestUtils.testTitle("Login with valid Account number : (" + validAccNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, validAccNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-h3.f-w-700.mb-0")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-h3.f-w-700.mb-0", "Login Successful");
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Hello OYEYINKA, Quickly start up using the frequent transactions section on the dashboard.')]", "Hello OYEYINKA, Quickly start up using the frequent transactions section on the dashboard.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(1000);
		
		logoutTest();
	}
	
	@Parameters ("testEnv")
	@Test
	public void emailLoginValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validEmail = (String) envs.get("validEmail");
		String wrongCharacterlength = (String) envs.get("wrongCharacterlength");
		String invalidPw = (String) envs.get("invalidPw");
		String wrongEmailFormat = (String) envs.get("wrongEmailFormat");
		
		// Login with valid email address and invalid password
		TestUtils.testTitle("Login with valid email address: (" + validEmail + ") and invalid password: (" + invalidPw + ")");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validEmail);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Enter Password
		getDriver().findElement(By.xpath("(//button[@type='button'])[3]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[2]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[7]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
		Thread.sleep(500);

		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with empty email addresss and valid password 
		TestUtils.testTitle("Login with empty email addresss: (  ) and valid password: (" + pw + ")");
		loginTest(testEnv, "  ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserName is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with email character Length greater than 256 and valid password
		TestUtils.testTitle("Login with email character Length greater than 256: (" + wrongCharacterlength + ") and valid password: (" + pw + ")");
		loginTest(testEnv, wrongCharacterlength);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserName is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with wrong email format email and valid password
		TestUtils.testTitle("Login with wrong email format email: (" + wrongEmailFormat	+ ") and valid password: (" + pw + ")");
		loginTest(testEnv, wrongEmailFormat);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Email or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with valid email address and empty password
		TestUtils.testTitle("Login with valid email address: (" + validEmail + ") and empty password: ( )");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validEmail);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: Password is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	}	
	
	@Parameters ("testEnv")
	@Test
	public void validEmailLoginTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validEmail = (String) envs.get("validEmail");
		
		// Login with valid Account number and valid password
		TestUtils.testTitle("Login with valid Account number : (" + validEmail + ") and valid password: (" + pw + ")");
		loginTest(testEnv, validEmail);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Login Successful')]", "Login Successful");
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Hello OLUSHINA, Quickly start up using the frequent transactions section on the dashboard.')]", "Hello OLUSHINA, Quickly start up using the frequent transactions section on the dashboard.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(1000);
		
		logoutTest();
	}
	
	public void logoutTest() throws Exception {
		 WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		 TestUtils.testTitle("Logout");
		 getDriver().findElement(By.xpath("//button[3]/span/mat-icon")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/button/span")));
		 TestUtils.assertSearchText("XPATH", "//h2", "You have been logged out!");
		 getDriver().findElement(By.xpath("//span/button/span")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
		 TestUtils.assertSearchText("XPATH", "//h1", "Login");
		 Thread.sleep(1000);
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void phoneNumberLoginValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validPhoneNum = (String) envs.get("validPhoneNum");
		String phoNumGreaterThan11Digits = (String) envs.get("phoNumGreaterThan11Digits");
		String invalidPw = (String) envs.get("invalidPw");
		String phoNumLessThan11Digits = (String) envs.get("phoNumLessThan11Digits");
		
		// Login with valid phone number and invalid password
		TestUtils.testTitle("Login with valid phone number: (" + validPhoneNum + ") and invalid password: (" + invalidPw + ")");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validPhoneNum);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Enter Password
		getDriver().findElement(By.xpath("(//button[@type='button'])[3]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[2]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[7]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
		Thread.sleep(500);

		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with empty phone number and valid password 
		TestUtils.testTitle("Login with empty phone number: (  ) and valid password: (" + pw + ")");
		loginTest(testEnv, "  ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "Bad Request: UserName is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with phone number Length greater than 11 digits and valid password
		TestUtils.testTitle("Login with phone number Length greater than 11 digits: (" + phoNumGreaterThan11Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, phoNumGreaterThan11Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with phone number Length less than 11 digits and valid password
		TestUtils.testTitle("Login with phone number Length less than 11 digits: (" + phoNumLessThan11Digits	+ ") and valid password: (" + pw + ")");
		loginTest(testEnv, phoNumLessThan11Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with valid Phone Number and empty password
		TestUtils.testTitle("Login with valid phone number: (" + validPhoneNum + ") and empty password: ( )");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validPhoneNum);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("CSSSELECTOR", "p.mat-body.small.ng-tns-c3-0", "Bad Request: Password is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	}	
	
	@Parameters ("testEnv")
	@Test
	public void validPhoneNumberLoginTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validPhoneNum = (String) envs.get("validPhoneNum");
		
		// Login with valid Account number and valid password
		TestUtils.testTitle("Login with valid Phone number : (" + validPhoneNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, validPhoneNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Login Successful')]", "Login Successful");
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Hello OLUSHINA, Quickly start up using the frequent transactions section on the dashboard.')]", "Hello OLUSHINA, Quickly start up using the frequent transactions section on the dashboard.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(1000);
		
		logoutTest();
	}
	
	@Parameters ("testEnv")
	@Test
	public void userIDLoginValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validUserID = (String) envs.get("validUserID");
		String alphanumericNum = (String) envs.get("alphanumericNum");
		String invalidPw = (String) envs.get("invalidPw");
		String specialCharnum = (String) envs.get("specialCharnum");
		String userIDGreaterThan12Digits = (String) envs.get("userIDGreaterThan12Digits");
		String userIDLessThan9Digits = (String) envs.get("userIDLessThan9Digits");
		
		// Login with valid User ID and invalid password
		TestUtils.testTitle("Login with valid User ID: (" + validUserID + ") and invalid password: (" + invalidPw + ")");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validUserID);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Enter Password
		getDriver().findElement(By.xpath("(//button[@type='button'])[3]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[2]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[7]")).click();
		getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
		Thread.sleep(500);

		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Username or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with empty user ID and valid password 
		TestUtils.testTitle("Login with empty User ID: (  ) and valid password: (" + pw + ")");
		loginTest(testEnv, "  ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: UserName is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with alphanumeric User ID and valid password
		TestUtils.testTitle("Login with Alphanumeric User ID: (" + alphanumericNum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, alphanumericNum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);

		// Login with User ID containing Special characters and valid password
		TestUtils.testTitle("Login with User ID containing Special characters: (" + specialCharnum + ") and valid password: (" + pw + ")");
		loginTest(testEnv, specialCharnum);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-Unable to validate Username or Password.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with User ID Length greater than 12 digits and valid password
		TestUtils.testTitle("Login with User ID Length greater than 12 digits: (" + userIDGreaterThan12Digits	+ ") and valid password: (" + pw + ")");
		loginTest(testEnv, userIDGreaterThan12Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);

		// Login with User ID Length less than 9 digits and valid password
		TestUtils.testTitle("Login with User ID Length less than 9 digits: (" + userIDLessThan9Digits + ") and valid password: (" + pw + ")");
		loginTest(testEnv, userIDLessThan9Digits);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "-UserId or Password incorrect.. Please try again");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		// Login with valid User ID and empty password
		TestUtils.testTitle("Login with valid User ID: (" + validUserID + ") and empty password: ( )");
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(validUserID);
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		// Submit button
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Bad Request: Password is required");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
	}	
	
	@Parameters ("testEnv")
	@Test
	public void validUserIDLoginTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validUserID = (String) envs.get("validUserID");
		
		// Login with valid Account number and valid password
		TestUtils.testTitle("Login with valid User ID : (" + validUserID + ") and valid password: (" + pw + ")");
		loginTest(testEnv, validUserID);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Login Successful')]", "Login Successful");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Hello OLUSHINA, Quickly start up using the frequent transactions section on the dashboard.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(1000);
		
		logoutTest();
	}
	
	@Parameters ("testEnv")
	@Test
	public void loginLinksValidationTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// Keypad
		TestUtils.testTitle("To confirm that there are only numbers, Clear button and Delete buttons on the Keypad");
		
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'1')]", "1");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'2')]", "2");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'3')]", "3");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'4')]", "4");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'5')]", "5");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'6')]", "6");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'7')]", "7");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'8')]", "8");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'9')]", "9");
		TestUtils.assertSearchText("XPATH", "//figure/button/span[contains(text(),'0')]", "0");
		TestUtils.assertSearchText("XPATH", "(//button[@type='button'])[12]", "CLR");
		TestUtils.assertSearchText("XPATH", "(//button[@type='button'])[13]", "DEL");
		    
		// Register Here
		TestUtils.testTitle("To confirm that user is directed to the Register Here page from Login page");
		getDriver().findElement(By.xpath("//a[contains(text(),'Register here')]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Get Login Details");
		TestUtils.assertSearchText("XPATH", "//form/button/span", "Register");
		Thread.sleep(500);
		
		//Navigate back to Login
		TestUtils.testTitle("To confirm that user is directed to the Login page from Register Here page");
		getDriver().findElement(By.linkText("Login here")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Login");
		
		// Forgot Password
		TestUtils.testTitle("To confirm that user is directed to the forgot Password page from Login page");
		getDriver().findElement(By.xpath("//a[contains(text(),'Forgot Password?')]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Recover Password");
		Thread.sleep(500);

		// Navigate back to Login
		TestUtils.testTitle("To confirm that user is directed to the Login page from Forgot Password page");
		getDriver().findElement(By.linkText("Login here")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h1", "Login");
		
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
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void rememberMeValidationTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("Login");

		String pw = (String) envs.get("pw");
		String validPhoneNum = (String) envs.get("validPhoneNum");
		
		// Masked Password
		TestUtils.testTitle("To confirm that password is visible when user clicks on the eye Icon");
		// Enter Password
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'1')]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'2')]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'3')]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'4')]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'5')]")).click();
		getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'6')]")).click();
		Thread.sleep(500);

		// Click on the eye icon
		getDriver().findElement(By.xpath("//span/mat-icon")).click();
		Thread.sleep(1000);

		String pass = getDriver().findElement(By.id("password")).getAttribute("value");
		testInfo.get().info(pass);

		// Remember Me button
		TestUtils.testTitle("To confirm that the Login ID is saved when the 'Remember me' checkbox is checked");
		testInfo.get().info("Login with valid Phone Number: <b>" + validPhoneNum + "</b> and password: <b>" + pw + "</b>");
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys(validPhoneNum);
	    
	    // Enter Password
	    getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'1')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'2')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'3')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'4')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'5')]")).click();
	    getDriver().findElement(By.xpath("//figure/button/span[contains(text(),'6')]")).click();
	    Thread.sleep(500);
	    
	    // Check the remember me checkbox
	    getDriver().findElement(By.xpath("//label/div")).click();
	    Thread.sleep(500);
	    
	    // Submit button
	    TestUtils.clickElement("XPATH", "//button[@type='submit']");
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
	    TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Login Successful')]", "Login Successful");
		Thread.sleep(500);
		
		logoutTest();
		TestUtils.testTitle("Assert Phone Number after Login");
		String userName = getDriver().findElement(By.id("username")).getAttribute("value");
		testInfo.get().info(userName);
	    
	}
}
