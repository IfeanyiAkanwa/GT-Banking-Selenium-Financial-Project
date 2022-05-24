package testCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class LoanProtect extends TestBase {
	@Test
public void navigateToLoanProtectTest() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Loans and Savings");
		
		//Click on Loans And Savings 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[8]/li/a/span[2]")));
		getDriver().findElement(By.xpath("//ul[8]/li/a/span[2]")).click();
		Thread.sleep(500);
		
		
		//CLick on Loan Protect
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Loan Protect')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Loan Protect')]", "Loan Protect");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Temporarily disable your quick credit");
		Thread.sleep(500);	
		
		
	}
@Test
public void loansAndSavingsValidationTest() throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	
	//Confirm that the Menu list is displayed and takes the user back to the Loans and Savings Page when clicked.
			
	
		LoansAndSavings.clickOnMenuList1();
	 
		//CLick on Loan Protect Again
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")).click();
		Thread.sleep(500);
				
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Loan Protect')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Loan Protect')]", "Loan Protect");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Temporarily disable your quick credit");
		Thread.sleep(500);	
				
		
}

@Test
@Parameters ("testEnv")
public void loanProtectTest(String testEnv
		) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
	JSONObject envs = (JSONObject) config.get("MaxAdvance");

		
	String token = (String) envs.get("token");
	
	
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-loan-protect/div/div/div/form/h2")));
	
	TestUtils.assertSearchText("XPATH", "//app-loan-protect/div/div/div/form/h2", "Enable or Disable your access to Quick Credit Loan.");
	Thread.sleep(500);
	
	TestUtils.assertSearchText("XPATH", "//app-loan-protect/div/div/div/form/div/h4", "Current Status");
	Thread.sleep(500);
	
	
	//getDriver().findElement(By.xpath(" //mat-slide-toggle")).click();
	
	WebElement checkStatus = getDriver().findElement(By.xpath(" //mat-slide-toggle"));
	
		if(checkStatus.isEnabled()) 
		{
		testInfo.get().info("Loan Protect is enabled");
		
		TestUtils.assertSearchText("XPATH", "//app-loan-protect/div/div/div/form/div[2]", "You will now have access to quick credit across all our channels.");
		
		
				
		// Enter Token
		getDriver().findElement(By.id("token")).click();
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		//Confirm that the submit button is enabled
		
		WebElement submitBtn = getDriver().findElement(By.id("submitBtn"));
		if(submitBtn.isEnabled()) {
		submitBtn.click();
					
					
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
					
		String msgAlert = getDriver().findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Yes'])[1]/following::mat-card[1]")).getText();
		TestUtils.assertSearchText("XPATH", "(.//*[normalize-space(text()) and normalize-space(.)='Yes'])[1]/following::mat-card[1]", msgAlert);
			
				
		}		
	
		else if (!checkStatus.isEnabled()) 
		{
			TestUtils.assertSearchText("XPATH", "//app-loan-protect/div/div/div/form/div[2]", " You will no longer have access to quick credit across all our channels.");
			
			//Enter Token
			getDriver().findElement(By.xpath("//mat-form-field/div/div/div[4]")).clear();
			getDriver().findElement(By.xpath("//mat-form-field/div/div/div[4]")).sendKeys(token);
			
			//click on submit 
			submitBtn.click();
			
			TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
			TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
			TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
			TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
			TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Loan Protect')]");
			
			String msgAlert = getDriver().findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Yes'])[1]/following::mat-card[1]")).getText();
			TestUtils.assertSearchText("XPATH", "(.//*[normalize-space(text()) and normalize-space(.)='Yes'])[1]/following::mat-card[1]", msgAlert);
			
		}
	
	}
}	
		
}

