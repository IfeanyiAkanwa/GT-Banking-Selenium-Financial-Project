package testCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.event.DragEvent;

import util.TestBase;
import util.TestUtils;

public class QuickCredit extends TestBase{

	
	@Test
	
public void navigateToQuickCreditTest() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Loans and Savings");
		
		//Click on Loans And Savings 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[8]/li/a/span[2]")));
		getDriver().findElement(By.xpath("//ul[8]/li/a/span[2]")).click();
		Thread.sleep(500);
		
		
		//CLick on Quick Credit
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[1]/figure/mat-card")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Quick Credit')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Quick Credit')]", "Quick Credit");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Get instant Loan");
		Thread.sleep(500);	
		
		
	}
	
@Test
	
	public void loansAndSavingsValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		//Confirm that the Menu list is displayed and takes the user back to the Loans and Savings Page when clicked.
				
		
			LoansAndSavings.clickOnMenuList1();
		 
		//CLick on Quick Credit Again
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]/figure/mat-card")));
			getDriver().findElement(By.xpath("//mat-grid-tile[1]/figure/mat-card")).click();
			Thread.sleep(500);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Quick Credit')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Quick Credit')]", "Quick Credit");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Get instant Loan");
			Thread.sleep(500);	
		 
}

@Test
@Parameters ("testEnv")
public void quickCreditEligibilityTest(String testEnv
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
	JSONObject envs = (JSONObject) config.get("QuickCredit");

	String secretAnswer = (String) envs.get("SecretAnswer");		
	String token = (String) envs.get("token");
	
	
	
	
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-quick-credit/div/div/form/div/div/p")));
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div/div/p", "YOU ARE ELIGIBLE FOR A LOAN UP TO");
	
	TestUtils.testTitle("COnfirm that loan amount is displayed");
	String loanAmt = getDriver().findElement(By.id("Editamount")).getText();
	TestUtils.assertSearchText("ID", "Editamount", loanAmt);
	testInfo.get().info(loanAmt);
	
	String loanRate = getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div/div/p[2]")).getText();
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div/div/p[2]", loanRate);
	
	// Input command for range slider here
	TestUtils.testTitle("COnfirm that the range slider is visible");
	
	TestUtils.assertSearchText("XPATH", "//input[@type='range']", "");
	getDriver().findElement(By.xpath("//input[@type='range']")).click();
	
	/*
	 * WebElement slider =
	 * getDriver().findElement(By.xpath("//input[@type='range']"));
	 * 
	 * ((JavascriptExecutor) driver).executeScript("scrollBy(10000,5000000);");
	 * Actions action = new Actions(getDriver());
	 * org.openqa.selenium.interactions.Action dragAndDrop =
	 * action.clickAndHold(slider).moveByOffset(3000000, 0).release().build();
	 * 
	 * //action.dragAndDropBy(slider, 10000, 3000000).perform();
	 * dragAndDrop.perform();
	 */
	 
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit[1]/div/div/form/div/div[2]/div/span[1]", "Drag slider to change loan amount.");
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div/div[3]/p", "CHANGE LOAN TENURE");
	
	//CLick on Select Loan Tenor
	
	getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div/div[3]/ng-select/div/span[2]")).click();
	Thread.sleep(500);
	
	getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div/div[3]/ng-select/div/span[2]")).click();
	Thread.sleep(500);
	
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[2]/h5", "REPAYMENT DETAILS");
	
	String mthlyRepaymt = getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div[2]/div/div[1]")).getText();
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[2]/div/div[1]", mthlyRepaymt);
	
	String totalRepaymt = getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div[2]/div/div[2]")).getText();
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[2]/div/div[2]", totalRepaymt);
	
	//Assert that there is an "Accept Loan Button
	
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[2]/div/button", "ACCEPT LOAN");
	getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div[2]/div/button")).click();
	Thread.sleep(500);
	
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-quick-credit/div/div/form/div[2]/div[1]")));
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[2]/small", "Enter your security details to confirm loan request");
	
	//Confirm that
	
	//Enter Secret Answer
	
	getDriver().findElement(By.id("secretAnswer")).clear();
	getDriver().findElement(By.id("secretAnswer")).sendKeys(secretAnswer);
			
	
	//Enter Token
	
	getDriver().findElement(By.id("token")).clear();
	getDriver().findElement(By.id("token")).sendKeys(token);
	
	//Confirm there is a reset and a submit button 
	TestUtils.testTitle("COnfirm that the reset and submit buttons are displayed ");
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[3]/div/div[3]/button[1]", "RESET");
	TestUtils.assertSearchText("XPATH", "//gtibank-quick-credit/div/div/form/div[3]/div/div[3]/button[2]", "SUBMIT");
	
	//click on the submit button
	TestUtils.scrollToElement("XPATH", "//gtibank-quick-credit/div/div/form/div[3]/div/div[3]/button[2]");
	getDriver().findElement(By.xpath("//gtibank-quick-credit/div/div/form/div[3]/div/div[3]/button[2]")).click();
	Thread.sleep(500);	
	
	/*
	 * TestUtils.scrollToElement("XPATH",
	 * "//gtibank-quick-credit/div/div/form/div");
	 * TestUtils.testTitle("Confirm that Loan Approved is Displayed");
	 * TestUtils.assertSearchText("XPATH",
	 * "//gtibank-quick-credit/div/div/form/div/div/button", "LOAN APPROVED");
	 * Thread.sleep(1000);
	 */
	
}



}
