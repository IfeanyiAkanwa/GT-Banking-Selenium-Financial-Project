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


import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class StandingOrder extends TestBase{
	
	
	@Test
	public void navigateToStandingOrderTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	

	
	TestUtils.testTitle("Navigate to Transfers");
	
	//Click on Transfers
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
	getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
	Thread.sleep(500);
	
	TestUtils.testTitle("Navigate To Standing Order");
	
	//CLick on Standing order
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[7]/figure/mat-card")));
	getDriver().findElement(By.xpath("//mat-grid-tile[7]/figure/mat-card")).click();
	Thread.sleep(500);
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Standing Order')]")));
	TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Standing Order')]", "Standing Order");
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
	Thread.sleep(500);		

	
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div/label[1][contains(text(),'GTBank')]", "GTBank");
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div/label[2]", "Other Banks");
	Thread.sleep(500);
	
}

@Test
public void transferValidationTest() throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	
	//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
	Transfers.clickOnMenuList1();
			
			
			//Click on Standing Order
			
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[7]/figure/mat-card")));
	getDriver().findElement(By.xpath("//mat-grid-tile[7]/figure/mat-card")).click();
	Thread.sleep(500);
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Standing Order')]")));
	TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Standing Order')]", "Standing Order");
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to anyone. Its Quick and Easy");
	Thread.sleep(500);		

	
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div/label[1][contains(text(),'GTBank')]", "GTBank");
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div/label[2]", "Other Banks");
	Thread.sleep(500);
			
			
			
			Transfers.frequentTransfers();
			TestUtils.raterTest("Transfer Via Standing Order");
			
}


@Parameters ("testEnv")
@Test
public void savedBeneficiaryGTBANKStandingOrder(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	
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
	JSONObject envs = (JSONObject) config.get("StandingOrder");

	//String nickName = (String) envs.get("nickname");
	String amount = (String) envs.get("amount");
	String Remark = (String) envs.get("Remark");		
	String token = (String) envs.get("token");
	
	//Select account to debit
	
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	Thread.sleep(500);
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	Thread.sleep(500);
	
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div[3]/div/label[1]", "Saved Beneficiary");
	TestUtils.assertSearchText("XPATH", "//app-standing-order/div/div/div/div/form/div[3]/div/label[2]", "New Beneficiary");
	
	

	// To confirm that Select Beneficiary drop down populates lists of Beneficiaries
		testInfo.get().info("<b> Select Beneficiary </b>");
		TestUtils.testTitle("To confirm that Select Beneficiary drop down shows the lists of Beneficiaries");
			
			
			
		getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div/span")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")));
		getDriver().findElement(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")).click();
		Thread.sleep(500);
			
			if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")) != null)
			{
				testInfo.get().info("Beneficiaries are displayed");
			}
			else 
			{
				testInfo.get().error("Beneficiaries are not displayed");
			}

	
			Assertion.otherValidationSavedBeneficiaryTest();
			
			//Enter Amount
			getDriver().findElement(By.id("transAmt")).clear();
			getDriver().findElement(By.id("transAmt")).sendKeys(amount);
			
			
			TestUtils.selectStartEndDate();
			
			//Enter Remarks. This is actually supposed to be secret answer.
			getDriver().findElement(By.xpath("//input[@type='textarea']")).clear();
			getDriver().findElement(By.xpath("//input[@type='textarea']")).sendKeys(Remark);
			TestUtils.scrollToElement("ID", "customer");
			
			
			
			// Click on Continue
			getDriver().findElement(By.id("customer")).click();
			Thread.sleep(2000);
			
			
			//Token confirmation page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
			TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
			
			// Enter Token
			getDriver().findElement(By.id("token")).clear();
			getDriver().findElement(By.id("token")).sendKeys(token);
			
			
			//Click on submit on Token COnfirmation page
			TestUtils.submitAndClose();
}


@Parameters ("testEnv")
@Test

public static void newBeneficiaryGTBANKStandingOrderTest(String testEnv) throws Exception {
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
	JSONObject envs = (JSONObject) config.get("NewBeneficiary");

	String account = (String) envs.get("account");
	String amount = (String) envs.get("amount");
	String Remark = (String) envs.get("Remark");		
	String token = (String) envs.get("token");
	
	
	
	TestUtils.testTitle("Navigate to New Beneficiary");
	TestUtils.clickElement("XPATH", "//app-standing-order/div/div/div/div/form/div[3]/div/label[2]");
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	
	
	//click on New
	getDriver().findElement(By.xpath("//app-standing-order/div/div/div/div/form/div[3]/div/label[2]")).click();
	Thread.sleep(500);
	
	
	
	//Enter Account Number 
	getDriver().findElement(By.xpath("//input[@id='beneficiary']")).clear();
	getDriver().findElement(By.xpath("//input[@id='beneficiary']")).sendKeys(account);
	
	
	Assertion.otherValidationNewBeneficiaryTest();
	
	//Enter Amount
	getDriver().findElement(By.id("transAmt")).clear();
	getDriver().findElement(By.id("transAmt")).sendKeys(amount);
	
	TestUtils.selectStartEndDate();
	
	//Enter Remarks. This is actually supposed to be secret answer.
	getDriver().findElement(By.xpath("//input[@type='textarea']")).clear();
	getDriver().findElement(By.xpath("//input[@type='textarea']")).sendKeys(Remark);
	TestUtils.scrollToElement("ID", "customer");
	
	// Click on Continue
	getDriver().findElement(By.id("customer")).click();
	Thread.sleep(2000);
	
	//Token confirmation page
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
	TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
	
	// Enter Token
	getDriver().findElement(By.id("token")).clear();
	getDriver().findElement(By.id("token")).sendKeys(token);
	
	
	//Click on submit on Token COnfirmation page
	TestUtils.submitAndClose();
			
			
}

@Parameters ("testEnv")
@Test
public void savedBeneficiaryOtherBanksStandingOrder(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	
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
	JSONObject envs = (JSONObject) config.get("StandingOrder");

	//String nickName = (String) envs.get("nickname");
	String amount = (String) envs.get("amount");
	String Remark = (String) envs.get("Remark");		
	String token = (String) envs.get("token");
	
	
	
	TestUtils.testTitle("Navigate to Other Banks");
	TestUtils.clickElement("XPATH", "//app-standing-order/div/div/div/div/form/div/label[2]");
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
	Thread.sleep(500);
	
	//Select account to debit
	
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	Thread.sleep(500);
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	Thread.sleep(500);
	
	

	// To confirm that Select Beneficiary drop down populates lists of Beneficiaries
		testInfo.get().info("<b> Select Beneficiary </b>");
		TestUtils.testTitle("To confirm that Select Beneficiary drop down shows the lists of Beneficiaries");
			
		//Click on Saved
		getDriver().findElement(By.xpath("//app-standing-order/div/div/div/div/form/div[3]/div/label[1]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")));
		getDriver().findElement(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")).click();
		Thread.sleep(500);
			
			if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/gtibank-beneficiary-dropdown-items/div/div")) != null)
			{
				testInfo.get().info("Beneficiaries are displayed");
			}
			else 
			{
				testInfo.get().error("Beneficiaries are not displayed");
			}

	
			Assertion.otherValidationSavedBeneficiaryTest();
			
			//Enter Amount
			getDriver().findElement(By.id("transAmt")).clear();
			getDriver().findElement(By.id("transAmt")).sendKeys(amount);
			
			
			TestUtils.selectStartEndDate();
			
			//Enter Remarks. This is actually supposed to be secret answer.
			getDriver().findElement(By.xpath("//input[@type='textarea']")).clear();
			getDriver().findElement(By.xpath("//input[@type='textarea']")).sendKeys(Remark);
			TestUtils.scrollToElement("ID", "customer");
			
			TestUtils.assertSearchText("XPATH", "//gtibank-charge-info/p", "You will not be charged for this transaction");
			
			
			// Click on Continue
			getDriver().findElement(By.id("customer")).click();
			Thread.sleep(2000);
			
			
			//Token confirmation page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
			TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
			
			// Enter Token
			getDriver().findElement(By.id("token")).clear();
			getDriver().findElement(By.id("token")).sendKeys(token);
			
			
			//Click on submit on Token COnfirmation page
	    	TestUtils.submitAndClose();
}




}
