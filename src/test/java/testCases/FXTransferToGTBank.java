package testCases;


import java.io.File;
import java.io.FileReader;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;


public class FXTransferToGTBank  extends TestBase{

	@Test
	public void navigateToFXTransferToGTBankTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to FX Transfer To GTBank");
		
		//Click on FX Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'FX Transfers')]")));
		getDriver().findElement(By.xpath("//span[contains(text(),'FX Transfers')]")).click();
		Thread.sleep(500);
		
		//Click on FX Transfer to GTbank
		
	
		
		TestUtils.testTitle("FX transfer to GT Bank is Visible");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Fx Transfer to GT Bank')]")));
		TestUtils.assertSearchText("XPATH", "//p[contains(text(),'Fx Transfer to GT Bank')]", "Fx Transfer to GT Bank");
		getDriver().findElement(By.xpath("//p[contains(text(),'Fx Transfer to GT Bank')]")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'GTBank FX Transfer')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'GTBank FX Transfer')]", "GTBank FX Transfer");
		TestUtils.assertSearchText("XPATH", "//p[contains(text(),'Guarantee Trust Bank FX transfer')]", "Guarantee Trust Bank FX transfer");
		Thread.sleep(500);
		
		//Back to Menu List and Back and Navigate to GtBank FX Transfer
		TestUtils.testTitle("To Confirm the menu list button directs the user back to FX Transfers view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Menu list')]")));
		getDriver().findElement(By.xpath("//span[contains(text(),'Menu list')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'FX Transfers')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'FX Transfers')]", "FX Transfers");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//p[contains(text(),'Fx Transfer to GT Bank')]")).click();
	
	}
	@Test
	
	public void transferValidationTest() throws InterruptedException {
		
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);	

			//Validate Fields 
			getDriver().findElement(By.id("account")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")));
			getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
			Thread.sleep(500);
			String accToDebit = getDriver().findElement(By.xpath("//label[contains(text(),'Account to Debit:')]")).getText();
			TestUtils.assertSearchText("XPATH", "//label[contains(text(),'Account to Debit:')]", accToDebit);
			Thread.sleep(500);
	
		//	Transfers.frequentTransfers();
			
		//	TestUtils.raterTest("Transfer To GTBank");
				
	}

	@Parameters ("testEnv")
	
	@Test
	public static void savedBeneficiaryTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("SavedBeneficiary");

		//String nickName = (String) envs.get("nickname");
		String amount = (String) envs.get("amount");
		String Remark = (String) envs.get("Remark");		
		String token = (String) envs.get("token");
		
		//Navigate to Saved Beneficiary
		//TestUtils.testTitle("Navigate To Saved Beneficiary Option");
		//TestUtils.assertSearchText("XPATH", "//mat-button-toggle[@value='SAVED']", "Saved");
		//Thread.sleep(500);
		
		//Confirm that the Search Beneficiary  message is displayed
	
		
		// To confirm that Select Beneficiary drop down populates lists of Beneficiaries
		testInfo.get().info("<b> Select Beneficiary </b>");
		TestUtils.testTitle("To confirm that Select Beneficiary drop down populates lists of Beneficiaries");
		
		getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div")).click();
		
		Thread.sleep(500);
	
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-beneficiary-dropdown-items/div/div")) != null)
		{
			testInfo.get().info("Beneficiaries are displayed");
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div")).click();
		}
		else 
		{
			testInfo.get().error("Beneficiaries are not displayed");
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div")).click();
		}
		
		//select beneficiary
		getDriver().findElement(By.id("beneficiary")).click();
		Thread.sleep(500); 
		getDriver().findElement(By.cssSelector("p.mat-body.mb-1")).click();
			
		String beneficiaryName = getDriver().findElement(By.xpath("//label[contains(text(),'Enter Beneficiary`s name:')]")).getText();
		TestUtils.assertSearchText("XPATH", "//label[contains(text(),'Enter Beneficiary`s name:')]", beneficiaryName);
		Thread.sleep(500);	
	
		Assertion.otherValidationSavedBeneficiaryTest();
		
		//select account to debit
		Thread.sleep(1000);
		getDriver().findElement(By.id("account")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")));
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		
		
		// Enter Amount
		String amtToTransfer = getDriver().findElement(By.xpath("//label[contains(text(),'Amount to transfer:')]")).getText();
		TestUtils.assertSearchText("XPATH", "//label[contains(text(),'Amount to transfer:')]", amtToTransfer);
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
		
		
		//Enter Remarks.
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
		
		//Payment Purpose
		Select drpPayPurpose = new Select(getDriver().findElement(By.xpath("//select")));
		drpPayPurpose.selectByVisibleText("Advertising services");
		Thread.sleep(500);
		//Agree Checkbox
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Click here to Agree')]", "Click here to Agree");
		getDriver().findElement(By.xpath("//span[contains(text(),'Click here to Agree')]")).click();

		
		Thread.sleep(1000);
		
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Continue')]");
		
		// Click on Continue
		getDriver().findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		//Click on Submit
		
		getDriver().findElement(By.xpath("//span[contains(text(),'Submit')]")).click();
    	Thread.sleep(500); 
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Loading..')]")));
    	Thread.sleep(2000);
    	String msg = getDriver().findElement(By.xpath("//p[contains(text(),'1001')]")).getText();
    	testInfo.get().info(msg);
 
		Thread.sleep(500);
						
		// Click Back button 
		getDriver().findElement(By.xpath("//span[contains(text(),'Back')]")).click();
		Thread.sleep(500);
		
		
	}
	
	@Parameters ("testEnv")
	@Test
	
	public static void newBeneficiaryTest(String testEnv) throws Exception {
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
		getDriver().findElement(By.xpath("//label[contains(text(),'New Beneficiary')]")).click(); 
		//select account to debit
		Thread.sleep(1000);
		getDriver().findElement(By.id("account")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")));
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);

		//FXTransfer without new Beneficiary Account Number
		testInfo.get().info("<b> FXTransfer WITHOUT new Beneficiary Account Number </b>");	
		
		//select account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Amount
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
		
		
		//Enter Remarks.
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
		
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Continue')]");
		
		//Payment Purpose
		Select drpPayPurpose = new Select(getDriver().findElement(By.xpath("//select")));
		drpPayPurpose.selectByVisibleText("Advertising services");
		Thread.sleep(500);
		//Agree Checkbox
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Click here to Agree')]", "Click here to Agree");
		getDriver().findElement(By.xpath("//span[contains(text(),'Click here to Agree')]")).click();

		
		Thread.sleep(1000);
		
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Continue')]");
		
		// Click on Continue
		getDriver().findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		//Submit
		getDriver().findElement(By.xpath("//span[contains(text(),'Submit')]")).click();
    	Thread.sleep(500); 
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Loading..')]")));
    	Thread.sleep(2000);
    	TestUtils.assertSearchText("XPATH", "//p[contains(text(),'- Beneficiary Name is required')]", "Bad Request: - Beneficiary Name is required");
    	String msg = getDriver().findElement(By.xpath("//p[contains(text(),'- Beneficiary Name is required')]")).getText();
    	testInfo.get().info(msg);

				
    	Thread.sleep(500);
		
		// Click Back button 
		getDriver().findElement(By.xpath("//span[contains(text(),'Back')]")).click();
		Thread.sleep(500);
		
		testInfo.get().info("<b> FXTransfer with new Beneficiary Account Number </b>");
		//Enter Account Number 
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).clear();
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).sendKeys(account);
		
		Thread.sleep(2000);
		Assertion.otherValidationNewBeneficiaryTest();
		
		// Click on Continue
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Continue')]");
		
		// Click on Continue
		getDriver().findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
			
		//Submit
		getDriver().findElement(By.xpath("//span[contains(text(),'Submit')]")).click();
		Thread.sleep(500); 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Loading..')]")));
		Thread.sleep(2000);
		String msg2 = getDriver().findElement(By.xpath("//p[contains(text(),'1001')]")).getText();
		testInfo.get().info(msg2);

		
		
		
	}
	
	
	
}
