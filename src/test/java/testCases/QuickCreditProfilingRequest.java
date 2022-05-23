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

public class QuickCreditProfilingRequest extends TestBase {
@Test
public void navigateToQuickCreditProfilingRequestTest() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Loans and Savings");
		
		//Click on Loans And Savings 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[8]/li/a/span[2]")));
		getDriver().findElement(By.xpath("//ul[8]/li/a/span[2]")).click();
		Thread.sleep(500);
		
		
		//CLick on Quick Credit Profiling Request
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[2]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[2]/figure/mat-card")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Quick Credit Profiler')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Quick Credit Profiler')]", "Quick Credit Profiler");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Get instant Loan");
		Thread.sleep(500);	
		
		
	}
@Test
public void loansAndSavingsValidationTest() throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	
	//Confirm that the Menu list is displayed and takes the user back to the Loans and Savings Page when clicked.
			
	
		LoansAndSavings.clickOnMenuList1();
	 
	//CLick on Quick Credit Profiling Request Again
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[2]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[2]/figure/mat-card")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Quick Credit Profiler')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Quick Credit Profiler')]", "Quick Credit Profiler");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Get instant Loan");
		Thread.sleep(500);	
	 
}
@Test
@Parameters ("testEnv")
public void quickCreditProfilingRequestTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	
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
	JSONObject envs = (JSONObject) config.get("QuickCredit");

	String email = (String) envs.get("email");		
	String salary = (String) envs.get("salary");	
	String token = (String) envs.get("token");
	
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-quick-credit-profiler/div/div/div/div/form/p")));
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit-profiler/div/div/div/div/form/p", "Quick Credit Profiler.");

	
	//select account to profile	
	
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	Thread.sleep(500);
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	Thread.sleep(500);
	
	//Enter email address
	getDriver().findElement(By.id("email")).clear();
	getDriver().findElement(By.id("email")).sendKeys(email);
	
	
	//Enter Salary
	getDriver().findElement(By.id("amount")).clear();
	getDriver().findElement(By.id("amount")).sendKeys(salary);
	
	//click on the continue button
	
	getDriver().findElement(By.id("submitBtn")).click();
	Thread.sleep(2000);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
	TestUtils.assertSearchText("XPATH", "//h3", "Token Confirmation");
	
	//Enter Token
	
	getDriver().findElement(By.id("token")).clear();
	getDriver().findElement(By.id("token")).sendKeys(token);
	
	//click on submit
	getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
	
	String msg = getDriver().findElement(By.xpath("//h3")).getText();
	String msg2 = getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div/p[2]")).getText();
	
	testInfo.get().info(msg + "\n" + msg2);
	
	//close page
	
	getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[2]/button")).click();
			
}


	
}
