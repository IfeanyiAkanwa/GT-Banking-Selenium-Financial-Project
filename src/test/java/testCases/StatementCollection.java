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

public class StatementCollection extends IRequire{
	
	@Test
	public void navigateToStatementCollectionTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		navigateToIRequireTest();
		IRequireValidationTest();
		
		// Click on statement Collection
		getDriver().findElement(By.xpath("//mat-tab-header/div[2]/div/div/div/div[contains(text(),'Statement Collection')]")).click();
		Thread.sleep(500);
		
	}
	
	@Test
	@Parameters ("testEnv")
	public void statementCollectionBranchSelfPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		String copies = (String) envs.get("copies");
			String token = (String) envs.get("token");
		
		TestUtils.testTitle("Confirm that you can see Account to Debit");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label[contains(text(),'Account To Debit')]", "Select Account To Debit:");
		
		getDriver().findElement(By.xpath("//input[@id='AccountToDebit']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Statement Account");
		getDriver().findElement(By.xpath("//input[@id='statementAccount']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Start Date : Year, Month and Day");
		getDriver().findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select Start Date'])[1]/following::*[name()='svg'][1]")).click();
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button/span[1]")).click();
		getDriver().findElement(By.xpath("//mat-multi-year-view/table/tbody/tr[6]/td[3]/div")).click();
		getDriver().findElement(By.xpath("//mat-year-view/table/tbody/tr[4]/td[4]/div")).click();
		getDriver().findElement(By.xpath("//mat-month-view/table/tbody/tr/td[4]/div")).click();
		
		
		TestUtils.testTitle("Select Stop Date : Year, Month and Day");
		getDriver().findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Stop Date'])[1]/following::*[name()='svg'][1]")).click();
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button/span")).click();
		getDriver().findElement(By.xpath("//mat-multi-year-view/table/tbody/tr[6]/td[4]/div")).click();
		getDriver().findElement(By.xpath("//div/mat-year-view/table/tbody/tr[3]/td/div")).click();
		getDriver().findElement(By.xpath("//mat-month-view/table/tbody/tr[4]/td[6]/div")).click();
		
		//Enter Number of Copies 
		getDriver().findElement(By.id("Copies")).clear();
		getDriver().findElement(By.id("Copies")).sendKeys(copies);
				
		
		
		
		TestUtils.testTitle("Confirm that you can see Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//gtibank-statement-collection/form/p", "Select Pickup Options");
		
		// To confirm that Select Pick Up Branches displays lists of Branches
		testInfo.get().info("<b> Select PickUp Branch </b>");
		TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
		TestUtils.clickElement("XPATH", "//ng-select[@id='branchList']/div");
		Thread.sleep(500);
					
					
		getDriver().findElement(By.xpath("//ng-select[@id='branchList']/div")).click();
		Thread.sleep(500);
		
		
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-select[@id='branchList']/div")) != null)
		{
			testInfo.get().info("Pick Up Branches are displayed");
		}
else 
		{
			testInfo.get().error("Pick Up Branches are not displayed");
		}
        
		//Select a branch 
				
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
		Thread.sleep(500);
		        
		//Select Pick up Option
		getDriver().findElement(By.id("PickUpOption")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
		
		//Click on Self Option
		TestUtils.testTitle("Customer selects Self Pick Up Option");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
		Thread.sleep(500);
		
		//Click on submit
		TestUtils.scrollToElement("XPATH", "//div[5]/div/button/span");	 
		getDriver().findElement(By.xpath("//div[5]/div/button/span")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
	
		//Click on submit on Token COnfirmation page
		getDriver().findElement(By.xpath("//div/div/div[3]/button[2]/span")).click();
		    	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		Thread.sleep(2000);
		    	
		    	
		String msg = getDriver().findElement(By.xpath("//h3")).getText();
		String msg2 = getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div/div/div")).getText();
		    	
		testInfo.get().info(msg + "\n " + msg2 );  
		    		
				
				//close page
				
		getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div[2]/button")).click();
				
				

	
	}
	
	@Test
	@Parameters ("testEnv")
	public void statementCollectionBranchThirdPartyPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		String PickUpName = (String) envs.get("PickUpName");
		String copies = (String) envs.get("copies");
			String token = (String) envs.get("token");
		
		TestUtils.testTitle("Confirm that you can see Account to Debit");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label[contains(text(),'Account To Debit')]", "Select Account To Debit:");
		
		getDriver().findElement(By.xpath("//input[@id='AccountToDebit']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Statement Account");
		getDriver().findElement(By.xpath("//input[@id='statementAccount']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Start Date : Year, Month and Day");
		getDriver().findElement(By.id("startDate")).click();
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button[2]")).click();
		getDriver().findElement(By.xpath("//tr/td[2]/div")).click();
		
		
		TestUtils.testTitle("Select Stop Date ");
		getDriver().findElement(By.id("endDate")).click();
		getDriver().findElement(By.xpath("//td[7]/div")).click();
		
		//Enter Number of Copies 
		
		TestUtils.testTitle("Enter Number of copies to be collected");
		getDriver().findElement(By.id("Copies")).clear();
		getDriver().findElement(By.id("Copies")).sendKeys(copies);
				
		
		
		
		TestUtils.testTitle("Confirm that you can see Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//gtibank-statement-collection/form/p", "Select Pickup Options");
		
		// To confirm that Select Pick Up Branches displays lists of Branches
				testInfo.get().info("<b> Select PickUp Branch </b>");
				TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
				TestUtils.clickElement("XPATH", "//ng-select[@id='branchList']/div");
				Thread.sleep(500);
							
							
				getDriver().findElement(By.xpath("//ng-select[@id='branchList']/div")).click();
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
						
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
				Thread.sleep(500);
				        
		        
		//Select Pick up Option
		getDriver().findElement(By.id("PickUpOption")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
		
		//Click on Self Option
		TestUtils.testTitle("Customer selects Third Party Pick Up Option");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		//Enter Third Party Pick Up Name
        getDriver().findElement(By.id("ThirdParty")).click();
        getDriver().findElement(By.id("ThirdParty")).clear();
		getDriver().findElement(By.id("ThirdParty")).sendKeys(PickUpName);
		
    	//Select ThirdParty Pick Up Identification Type
		getDriver().findElement(By.id("Identification")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "National ID");
    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "International Passport");
    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "Drivers Licence");
    	
    	
    	//Select PickUp ID
    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
        Thread.sleep(500);
		
		//Click on submit
		TestUtils.scrollToElement("XPATH", "//div[5]/div/button/span");	 
		getDriver().findElement(By.xpath("//div[5]/div/button/span")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
	
		//Click on submit on Token COnfirmation page
		getDriver().findElement(By.xpath("//div/div/div[3]/button[2]/span")).click();
		    	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		Thread.sleep(2000);
		    	
		    	
		String msg = getDriver().findElement(By.xpath("//h3")).getText();
		String msg2 = getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div/div/div")).getText();
		    	
		testInfo.get().info(msg + "\n " + msg2 );  
		    		
				
				//close page
				
		getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div[2]/button")).click();
				
				

	
	}
	
	@Test
	@Parameters ("testEnv")
	
	public void statementCollectionOrangeLockerSelfPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		String copies = (String) envs.get("copies");
			String token = (String) envs.get("token");
		
		TestUtils.testTitle("Confirm that you can see Account to Debit");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label[contains(text(),'Account To Debit')]", "Select Account To Debit:");
		
		getDriver().findElement(By.xpath("//input[@id='AccountToDebit']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Statement Account");
		getDriver().findElement(By.xpath("//input[@id='statementAccount']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Start Date : Year, Month and Day");
		getDriver().findElement(By.id("startDate")).click();
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button[2]")).click();
		getDriver().findElement(By.xpath("//tr/td[2]/div")).click();
		
		
		TestUtils.testTitle("Select Stop Date ");
		getDriver().findElement(By.id("endDate")).click();
		getDriver().findElement(By.xpath("//td[7]/div")).click();
		
		//Enter Number of Copies 
		getDriver().findElement(By.id("Copies")).clear();
		getDriver().findElement(By.id("Copies")).sendKeys(copies);
				
		
		
		
		TestUtils.testTitle("Confirm that you can see Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//gtibank-statement-collection/form/p", "Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Orange Locker')]", "Orange Locker");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Branch')]", "Branch");
		
		//CLick on Orange Locker 
		
		getDriver().findElement(By.xpath("//div/div[contains(text(), 'Orange Locker')]")).click();
		
		// To confirm that Select Pick Up Branches displays lists of Branches
		testInfo.get().info("<b> Select PickUp Branch </b>");
		TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
		TestUtils.clickElement("XPATH", "//ng-select[@id='branch']/div");
		Thread.sleep(500);
					
					
		getDriver().findElement(By.xpath("//ng-select[@id='branch']/div")).click();
		Thread.sleep(500);
		
		
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-select[@id='branch']/div")) != null)
		{
			testInfo.get().info("Pick Up Branches are displayed");
		}
else 
		{
			testInfo.get().error("Pick Up Branches are not displayed");
		}
        
		//Select a branch 
				
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
		Thread.sleep(500);
		        
		//Select Pick up Option
		getDriver().findElement(By.id("PickUpOption")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
		
		//Click on Self Option
		TestUtils.testTitle("Customer selects Self Pick Up Option");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
		Thread.sleep(500);
		
		//Click on submit
		TestUtils.scrollToElement("XPATH", "//div[5]/div/button/span");	 
		getDriver().findElement(By.xpath("//div[5]/div/button/span")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
	
		//Click on submit on Token COnfirmation page
		getDriver().findElement(By.xpath("//div/div/div[3]/button[2]/span")).click();
		    	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		Thread.sleep(2000);
		    	
		    	
		String msg = getDriver().findElement(By.xpath("//h3")).getText();
		String msg2 = getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div/div/div")).getText();
		    	
		testInfo.get().info(msg + "\n " + msg2 );  
		    		
				
				//close page
				
		getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div[2]/button")).click();
				
				

	
	}
	
	@Test
	@Parameters ("testEnv")
	public void statementCollectionOrangeLockerThirdPartyPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		String PickUpName = (String) envs.get("PickUpName");
		String copies = (String) envs.get("copies");
			String token = (String) envs.get("token");
		
		TestUtils.testTitle("Confirm that you can see Account to Debit");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label[contains(text(),'Account To Debit')]", "Select Account To Debit:");
		
		getDriver().findElement(By.xpath("//input[@id='AccountToDebit']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Statement Account");
		getDriver().findElement(By.xpath("//input[@id='statementAccount']")).click();
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		
		TestUtils.testTitle("Select Start Date : Year, Month and Day");
		getDriver().findElement(By.id("startDate")).click();
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button[2]")).click();
		getDriver().findElement(By.xpath("//tr/td[2]/div")).click();
		
		
		TestUtils.testTitle("Select Stop Date ");
		getDriver().findElement(By.id("endDate")).click();
		getDriver().findElement(By.xpath("//td[7]/div")).click();
		
		//Enter Number of Copies 
		
		TestUtils.testTitle("Enter Number of copies to be collected");
		getDriver().findElement(By.id("Copies")).clear();
		getDriver().findElement(By.id("Copies")).sendKeys(copies);
				
		
		
		
		TestUtils.testTitle("Confirm that you can see Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//gtibank-statement-collection/form/p", "Select Pickup Options");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Orange Locker')]", "Orange Locker");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Branch')]", "Branch");
		//
		//CLick on Orange Locker 
		
		getDriver().findElement(By.xpath("//div/div[contains(text(), 'Orange Locker')]")).click();
		
		// To confirm that Select Pick Up Branches displays lists of Branches
		testInfo.get().info("<b> Select PickUp Branch </b>");
		TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
		TestUtils.clickElement("XPATH", "//ng-select[@id='branch']/div");
		Thread.sleep(500);
							
							
		getDriver().findElement(By.xpath("//ng-select[@id='branch']/div")).click();
		Thread.sleep(500);
				
				
		if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-select[@id='branch']/div")) != null)
		{
			testInfo.get().info("Pick Up Branches are displayed");
		}
		else 
		{
			testInfo.get().error("Pick Up Branches are not displayed");
		}
				//Select a branch 
						
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
				Thread.sleep(500);
				        
		        
		//Select Pick up Option
		getDriver().findElement(By.id("PickUpOption")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
		
		//Click on Self Option
		TestUtils.testTitle("Customer selects Third Party Pick Up Option");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		//Enter Third Party Pick Up Name
        getDriver().findElement(By.id("ThirdParty")).click();
        getDriver().findElement(By.id("ThirdParty")).clear();
		getDriver().findElement(By.id("ThirdParty")).sendKeys(PickUpName);
		
    	//Select ThirdParty Pick Up Identification Type
		getDriver().findElement(By.id("Identification")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "National ID");
    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "International Passport");
    	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "Drivers Licence");
    	
    	
    	//Select PickUp ID
    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
        Thread.sleep(500);
		
		//Click on submit
		TestUtils.scrollToElement("XPATH", "//div[5]/div/button/span");	 
		getDriver().findElement(By.xpath("//div[5]/div/button/span")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
		TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
		
		// Enter Token
		getDriver().findElement(By.id("token")).clear();
		getDriver().findElement(By.id("token")).sendKeys(token);
	
		//Click on submit on Token COnfirmation page
		getDriver().findElement(By.xpath("//div/div/div[3]/button[2]/span")).click();
		    	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		Thread.sleep(2000);
		    	
		    	
		String msg = getDriver().findElement(By.xpath("//h3")).getText();
		String msg2 = getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div/div/div")).getText();
		    	
		testInfo.get().info(msg + "\n " + msg2 );  
		    		
				
				//close page
				
		getDriver().findElement(By.xpath("//gtibank-statement-collection/div/div/div[2]/button")).click();
				
				

	
	}

}
