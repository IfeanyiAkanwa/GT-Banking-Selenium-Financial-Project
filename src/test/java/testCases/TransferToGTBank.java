package testCases;


import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.hamcrest.core.Is;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;


public class TransferToGTBank  extends TestBase{

	@Test
	public void navigateToTransferToGTBankTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Transfer To GTBank");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
		getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
		Thread.sleep(500);
		
		//Click on Transfer to GTbank
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		getDriver().findElement(By.xpath("//mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'GTBank Transfers')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'GTBank Transfers')]", "GTBank Transfers");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money to GTBank Account Holders");
		Thread.sleep(500);		
		
	}
	@Test
	
	public void transferValidationTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
				
				TestUtils.testTitle("To confirm that when user clicks on 'Menu List', user is directed back to the main Transfer Module");
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div[2]/a/span/span", "Menu list");
				getDriver().findElement(By.xpath("//gtibank-pageheader/div/div/div[2]/a/span/span")).click();
				TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Transfer money between your accounts or other accounts.");
				Thread.sleep(500);
				
				//Click on Transfer to GTbank again
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
				getDriver().findElement(By.xpath("//mat-card/p")).click();
				Thread.sleep(500);
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
		
		
		//click on saved
		//getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div")).click();
		//Thread.sleep(500);
		
		// To confirm that Select Beneficiary drop down populates lists of Beneficiaries
		testInfo.get().info("<b> Select Beneficiary </b>");
		TestUtils.testTitle("To confirm that Select Beneficiary drop down populates lists of Beneficiaries");
		
		TestUtils.clickElement("XPATH", "//gtibank-beneficiary-dropdown[@id='savedBeneficiaryDropdown']/ng-select/div");
		Thread.sleep(500);
		
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/a");
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-select/div/span")));
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		
		/*List<WebElement> resultList = getDriver().findElements(By.xpath("//ng-dropdown-panel/div/div[2]/div"));
        for (WebElement resultItem : resultList)
        {
           String tabname = resultItem.getText();
           testInfo.get().info("<b>"+ tabname + "</b>");
        }*/
	
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-beneficiary-dropdown-items/div/div")) != null)
		{
			testInfo.get().info("Beneficiaries are displayed");
		}
		else 
		{
			testInfo.get().error("Beneficiaries are not displayed");
		}
		
      //select beneficiary
        getDriver().findElement(By.xpath("//gtibank-beneficiary-dropdown-items/div/div")).click();
		Thread.sleep(500);
		
		Assertion.otherValidationSavedBeneficiaryTest();
		
		//select account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Amount
		
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
		
		
		
		//Enter Account Number 
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).clear();
		getDriver().findElement(By.xpath("//input[@id='beneficiary']")).sendKeys(account);
		
		
		Assertion.otherValidationNewBeneficiaryTest();
				
		//select account to debit
		getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		// Enter Amount
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
	
	
	
}
