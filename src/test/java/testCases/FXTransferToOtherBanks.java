package testCases;

import java.io.File;

import java.io.FileReader;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.By;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import junit.framework.Assert;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class FXTransferToOtherBanks extends TestBase{
	@Test
	public void navigateToTransferToOtherBanksTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		TestUtils.testTitle("Navigate to FX Transfer To Other Banks");
		
		//Click on FX Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'FX Transfers')]")));
		getDriver().findElement(By.xpath("//span[contains(text(),'FX Transfers')]")).click();
		Thread.sleep(500);
		
		//Click on FX Transfer to GTbank
		
		TestUtils.testTitle("FX transfer to Other Bank is Visible");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Fx Transfer to Other Banks')]")));
		TestUtils.assertSearchText("XPATH", "//p[contains(text(),'Fx Transfer to Other Banks')]", "Fx Transfer to Other Banks");
		getDriver().findElement(By.xpath("//p[contains(text(),'Fx Transfer to Other Banks')]")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Other Bank Fx Transfer')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Other Bank Fx Transfer')]", "Other Bank Fx Transfer");
		TestUtils.assertSearchText("XPATH", "//p[contains(text(),'other bank fx transfer')]", "other bank fx transfer");
		Thread.sleep(500);
		
		//Back to Menu List and Back and Navigate to GtBank FX Transfer
		TestUtils.testTitle("To Confirm the menu list button directs the user back to FX Transfers view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Menu list')]")));
		getDriver().findElement(By.xpath("//span[contains(text(),'Menu list')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'FX Transfers')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'FX Transfers')]", "FX Transfers");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//p[contains(text(),'Fx Transfer to Other Banks')]")).click();
	
		Thread.sleep(500);		
				
	}
	@Test
	public void transferValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
//		if (!getDriver().findElement(By.xpath("//span[contains(text(),'Continue')]")).isEnabled()){
//			testInfo.get().info("<b> The Continue button is disabled </b>");
//		} else {
//			testInfo.get().error("<b> The continue button is enabled </b>");
//		}
	
		TestUtils.assertSearchText("XPATH", "//label[contains(text(),'Account to Debit:')]", "Account to Debit:");
		Thread.sleep(500);	
		
		TestUtils.assertSearchText("XPATH", "//label[contains(text(),'Account to Charge:')]", "Account to Charge:");
		Thread.sleep(500);
	
		//To confirm Senders Details is present 
		TestUtils.testTitle("Confirm Sender Details");
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Sender Details')]", "Sender Details");
		
		TestUtils.testTitle("Confirm Beneficiary Details");
		TestUtils.assertSearchText("XPATH", "//h3[contains(text(),'Beneficiary Details')]", "Beneficiary Details");
		
		TestUtils.testTitle("Confirm Additional Information");
		TestUtils.assertSearchText("XPATH", "//h3[contains(text(),'Additional Information')]", "Additional Information");
//		
//	
//		Thread.sleep(500);	
//		Transfers.frequentTransfers();
//				
//	    TestUtils.raterTest("Transfer To Other Banks");
	}
	
	@Parameters ("testEnv")
	@Test
	
	public static void savedOtherBanksBeneficiaryTest(String testEnv) throws Exception {
	
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
		String BenInstitution = (String) envs.get("BenInstitution");
		String BenAddress = (String) envs.get("BenAddress");
		
		//select account to debit
		testInfo.get().info("<b> Sender Details </b>");
		TestUtils.testTitle("To confirm Account to Debit drop down populates lists of Debit Account");
		getDriver().findElement(By.id("account")).click();
		Thread.sleep(500);
        
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-dropdown-panel/div/div/div/p")) != null)
		{
			testInfo.get().info("Debit Account dropdown is populated");
		}
		else 
		{
			testInfo.get().error("Debit Account dropdown is not populated");
		}
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		//select Account to Charge
		TestUtils.testTitle("To confirm Account to Charge drop down populates lists of Charge Account");
		getDriver().findElement(By.xpath("//div[3]/gtibank-accounts-typeahead/div/ng-select/div")).click();
		
		Thread.sleep(500);
		
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-dropdown-panel/div/div/div/p")) != null)
		{
			testInfo.get().info("Charge Account dropdown is populated");
		}
		else 
		{
			testInfo.get().error("Charge Account dropdown is not populated");
		}
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		
		// Enter Amount
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
		
		//Source of Funds
		Select drpSourceOfFunds = new Select(getDriver().findElement(By.xpath("//div[5]/select")));
		drpSourceOfFunds.selectByVisibleText("Company Sale");  
		Thread.sleep(500);
		
		//Nature of Senders Business
		TestUtils.scrollToElement("XPATH", "//div[6]/select");
		Select drpSendersBiz = new Select(getDriver().findElement(By.xpath("//div[6]/select")));
		drpSendersBiz.selectByVisibleText("Banking");  
		Thread.sleep(500);

		// To confirm that Select Beneficiary drop down populates lists of Beneficiaries  
		testInfo.get().info("<b> Beneficiary Details </b>");
		TestUtils.testTitle("To confirm that Select Beneficiary drop down populates lists of Beneficiaries");
		Thread.sleep(500);
		
		 //select beneficiary
		getDriver().findElement(By.id("beneficiary")).click();
        
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-beneficiary-dropdown-items/div/div")) != null)
		{
			testInfo.get().info("Beneficiaries are displayed");
		}
		else 
		{
			testInfo.get().error("Beneficiaries are not displayed");
		}
		Thread.sleep(500);
		
		String BeneficiaryName = getDriver().findElement(By.xpath("//div[1]/gtibank-beneficiary-dropdown-items/div/div/div/p")).getText(); 
		
		
		getDriver().findElement(By.xpath("//div[1]/gtibank-beneficiary-dropdown-items/div/div/div/p")).click();
		
		Thread.sleep(3000);
		
		//Confirm that the Search Beneficiary  message is displayed
		
		Assertion.otherValidationSavedBeneficiaryTest();
		
		//Country
		Select country = new Select(getDriver().findElement(By.xpath("//div[11]/select")));
		country.selectByVisibleText("NIGERIA");  
		Thread.sleep(500);
		
		//Nature of Beneficiary's Business
		Select drpBeneficiaryBiz = new Select(getDriver().findElement(By.xpath("//div[12]/select")));
		drpBeneficiaryBiz.selectByVisibleText("Banking");  
		Thread.sleep(500);

		// Enter beneficiary's institution 
		getDriver().findElement(By.id("BenInstitution")).clear();
		getDriver().findElement(By.id("BenInstitution")).sendKeys(BenInstitution);
		
		//Relationship
		Select senderBeneficiaryRelationship = new Select(getDriver().findElement(By.xpath("//div[14]/select")));
		senderBeneficiaryRelationship.selectByVisibleText("Donor");  
		Thread.sleep(500);
		
		// Enter Ben bank Name
		getDriver().findElement(By.id("BenBankAddress")).clear();
		getDriver().findElement(By.id("BenBankAddress")).sendKeys(BenAddress);
		
		//click NO intermediary Bank
		getDriver().findElement(By.xpath("//div[19]/div/label[2]")).click(); //div[19]/div/label[2]
		
		
		testInfo.get().info("<b> Additional Information </b>");
		TestUtils.testTitle("Populate Additional Information");
		Thread.sleep(500);
		
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Transfers for Commercial purposes to account in ot')]", "Transfers "
				+ "for Commercial purposes to account in other banks within Nigeria are not allowed in line with CBN regulation.");
		getDriver().findElement(By.xpath("//span[contains(text(),'Transfers for Commercial purposes to account in ot')]")).click();
		
		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[27]/select")));
		//Payment Of Purpose
		TestUtils.scrollToElement("XPATH", "//input[@placeholder='Enter Remark']");
		Thread.sleep(500);
		Select drpPayPurpose = new Select(getDriver().findElement(By.xpath("//div[26]/select")));
		drpPayPurpose.selectByVisibleText("Advertising services");
		Thread.sleep(500);
		
		//Enter Remarks.
		getDriver().findElement(By.xpath("//input[@placeholder='Enter Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Enter Remark']")).sendKeys(Remark);
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Continue')]");
		
		//Agree Checkbox
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Click here to Agree')]", "Click here to Agree");
		getDriver().findElement(By.xpath("//span[contains(text(),'Click here to Agree')]")).click();
		
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
	
	//@Test
	//@Parameters ("testEnv")

	/*public void newOtherBanksBeneficiary(String testEnv) throws Exception, IOException, ParseException {
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

		
		//String bank = (String) envs.get("bank");
		String account = (String) envs.get("account");
		String amount = (String) envs.get("amount");
		String Remark = (String) envs.get("Remark");		
		String token = (String) envs.get("token");
		
		
		
		TestUtils.testTitle("Navigate to New Beneficiary");
		TestUtils.clickElement("XPATH", "//mat-button-toggle[@value='NEW']");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
		Thread.sleep(500);
				
		//click on New
		getDriver().findElement(By.xpath("//mat-button-toggle[@value='NEW']")).click();
		Thread.sleep(500);
		
		
		//Select Beneficiary Bank
		
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		
		List<WebElement> resultList = getDriver().findElements(By.xpath("//ng-dropdown-panel/div/div[2]/div"));
        for (WebElement resultItem : resultList)
        {
           String tabname = resultItem.getText();
           testInfo.get().info("<b>"+ tabname + "</b>");
        }
        
      //select beneficiary
        getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("beneficiary")));
		Thread.sleep(500);
		
		
		
		//Enter Account Number 
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).clear();
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).sendKeys(account);
		
				
		//select account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='accountToDebit']/div")).click();
		Thread.sleep(500);
		//getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		//Thread.sleep(500);
		
		// Enter Amount
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
		
		// Enter second Amount //This should be removed
		getDriver().findElement(By.id("transAmt")).clear();
		getDriver().findElement(By.id("transAmt")).sendKeys(amount);
		
		
		//Enter Remarks. This is actually supposed to be secret answer.
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).clear();
		getDriver().findElement(By.xpath("//input[@placeholder='Remark']")).sendKeys(Remark);
		TestUtils.scrollToElement("ID", "submitBtn");
		
		// Click on Continue
		getDriver().findElement(By.id("submitBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
		
		//Click on Submit
		//getDriver().findElement(By.xpath("xpath=//div[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-transfers/div/div/app-gt-transfers/div/div/div/app-token-confirmation-modal/div/div/div[3]/button[2]")).click();
		//Thread.sleep(500);
		//testInfo.get().info("<b> Transfer was successful </b>");
				
		// Click Back button 
		getDriver().findElement(By.xpath("//div[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-transfers/div/div/app-gt-transfers/div/div/div/app-token-confirmation-modal/div/div/div[3]/button")).click();
		Thread.sleep(500);
		
	}
	*/
	
}
