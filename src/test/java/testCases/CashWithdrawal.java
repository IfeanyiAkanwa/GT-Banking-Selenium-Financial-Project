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

import util.TestUtils;

public class CashWithdrawal extends IRequire{
	
	@Test
	public void navigateToCashWithdrawalTest()throws InterruptedException {
		
	navigateToIRequireTest();
	IRequireValidationTest();

	String textCash = getDriver().findElement(By.xpath("//mat-tab-header/div[2]/div/div/div/div")).getText();
	TestUtils.assertSearchText("XPATH", "//mat-tab-header/div[2]/div/div/div/div", textCash);
	
	
	//To confirm that the color of the Checked Item is Orange
	System.out.println(getDriver().findElement(By.xpath("//mat-tab-header/div[2]/div/div/div/div")).getCssValue("background-color"));
	Thread.sleep(1000);
	
	}
	@Parameters ("testEnv")
	@Test
	public void selfCashWithdrawalTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
		JSONObject envs = (JSONObject) config.get("SelfPickUp");


			String amount = (String) envs.get("amount");
			String Remark = (String) envs.get("Remark");
			String token = (String) envs.get("token");
		
			
			//Enter Amount
			getDriver().findElement(By.id("Amount")).clear();
			getDriver().findElement(By.id("Amount")).sendKeys(amount);
			
			
			//Select Account to Debit
			TestUtils.testTitle("Customer Selects Account to Debit");
			getDriver().findElement(By.xpath("//ng-select[@id='account']/div/span")).click();
			Thread.sleep(500);
			
			getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[contains(text(), 'SAVINGS ACCOUNT')]")).click();
			Thread.sleep(500);
			
			//Enter Remarks
			getDriver().findElement(By.id("Remark")).clear();
			getDriver().findElement(By.id("Remark")).sendKeys(Remark);
			
			
			// To confirm that Select Pick Up Branches displays lists of Branches
			testInfo.get().info("<b> Select PickUp Branch </b>");
			TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
			TestUtils.clickElement("XPATH", "//ng-select[@id='branchList']/div/span");
			Thread.sleep(500);
			
			
			getDriver().findElement(By.xpath("//ng-select[@id='branchList']/div/span")).click();
			Thread.sleep(500);
			
			
	        
	        if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-select[@id='branchList']/div/span")) != null)
			{
				testInfo.get().info("Pick Up Branches are displayed");
			}
			else 
			{
				testInfo.get().error("Pick Up Branches are not displayed");
			}
	        
	        //Select a branch 
			
	        getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
	        Thread.sleep(500);
	        
	        //Select Pick up Option
	        getDriver().findElement(By.xpath("//gtibank-branchpickup/form/div[2]/ng-select/div")).click();
	        Thread.sleep(500);
	        TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
	    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
	    	
	    	//Click on Self Option
	    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
	        Thread.sleep(500);
	    	   	
	    	
	    	
	    	//Click on submit
	        TestUtils.scrollToElement("XPATH", "//button[@type='submit']");	 
	    	getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	    	
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
			TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
			
			// Enter Token
			getDriver().findElement(By.id("token")).clear();
			getDriver().findElement(By.id("token")).sendKeys(token);
			
			
			//Click on submit on Token COnfirmation page
	    	getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
	    	
	    	String msg = getDriver().findElement(By.xpath("//h3")).getText();
	    	String msg2 = getDriver().findElement(By.xpath("//gtibank-cash-withdrawal/div/div/div/div/div")).getText();
	    	if(msg == "Request Failed") {
	    	testInfo.get().info(msg + "\n "  + msg2 );
	    	
	    	}
	    	else {
	    		testInfo.get().info(msg + "\n " + msg2 );  
	    		}
			
			//close page
			
			getDriver().findElement(By.xpath("//gtibank-cash-withdrawal/div/div/div[2]/button")).click();
			
			
	}
	@Parameters ("testEnv")
	@Test
	public void thirdPartyCashWithdrawalTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
		JSONObject envs = (JSONObject) config.get("3rdPartyPickUP");


			String amount = (String) envs.get("amount");
			String Remark = (String) envs.get("Remark");
			String token = (String) envs.get("token");
			String PickUpName = (String) envs.get("PickUpName");
		
			
			//Enter Amount
			getDriver().findElement(By.id("Amount")).clear();
			getDriver().findElement(By.id("Amount")).sendKeys(amount);
			
			
			//Select Account to Debit
			TestUtils.testTitle("Customer Selects Account to Debit");
			getDriver().findElement(By.xpath("//ng-select[@id='account']/div/span")).click();
			Thread.sleep(500);
			
			getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[contains(text(), 'SAVINGS ACCOUNT')]")).click();
			Thread.sleep(500);
			
			//Enter Remarks
			getDriver().findElement(By.id("Remark")).clear();
			getDriver().findElement(By.id("Remark")).sendKeys(Remark);
			
			//Select Branch
			
			getDriver().findElement(By.xpath("//ng-select[@id='branchList']/div/span")).click();
			Thread.sleep(500);
			
			
	        getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
	        Thread.sleep(500);
	        
	        //Select Pick up Option
	        getDriver().findElement(By.xpath("//gtibank-branchpickup/form/div[2]/ng-select/div")).click();
	        Thread.sleep(500);
	      
	    	
	    	//Click on Third Party Option
	    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
	        Thread.sleep(500);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-branchpickup/form/div[3]")));
	       
	        //Enter Third Party Pick Up Name
	        getDriver().findElement(By.id("ThirdParty")).click();
	        getDriver().findElement(By.id("ThirdParty")).clear();
			getDriver().findElement(By.id("ThirdParty")).sendKeys(PickUpName);
			
	    	//Select ThirdParty Pick Up Identification Type
			getDriver().findElement(By.xpath("//gtibank-branchpickup/form/div[4]/ng-select/div/span")).click();
	        Thread.sleep(500);
	        TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "National ID");
	    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "International Passport");
	    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "Drivers Licence");
	    	
	    	
	    	//Select PickUp ID
	    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
	        Thread.sleep(500);
	    	
	    	//Click on submit
	        TestUtils.scrollToElement("XPATH", "//button[@type='submit']");	 
	    	getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	    	
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
			TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
			
			// Enter Token
			getDriver().findElement(By.id("token")).clear();
			getDriver().findElement(By.id("token")).sendKeys(token);
			
			
			//Click on submit on Token COnfirmation page
	    	getDriver().findElement(By.xpath("//app-token-confirmation-modal/div/div/div[3]/button[2]/span")).click();
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
	    	
	    	String msg = getDriver().findElement(By.xpath("//h3")).getText();
	    	String msg2 = getDriver().findElement(By.xpath("//gtibank-cash-withdrawal/div/div/div/div/div")).getText();
	    	
	    	testInfo.get().info(msg + "\n" + msg2);
	    	
						
			//close page
			
			getDriver().findElement(By.xpath("//gtibank-cash-withdrawal/div/div/div[2]/button")).click();
			
			
	}
	
	
}