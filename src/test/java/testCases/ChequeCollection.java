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

public class ChequeCollection extends IRequire {
	@Test
public void navigateToChequeCollectionTest() throws InterruptedException {
		
		navigateToIRequireTest();
		IRequireValidationTest();
		
		
		getDriver().findElement(By.xpath("//mat-tab-header/div[3]")).click();
		
		
		getDriver().findElement(By.xpath("//mat-tab-header/div[2]/div/div/div/div[contains(text(),'Cheque Book Collection')]")).click();
		Thread.sleep(500);
		
	}

@Test
@Parameters ("testEnv")
public void chequeCollectionBranchSelfPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		
		String token = (String) envs.get("token");
		String leaves = (String) envs.get("leaves");
	
	TestUtils.testTitle("Confirm that you can see Account to Debit");
	TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Select Account To Debit:");
	
	//Select Account to debit
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	
	//Enter Number of Leaves
	TestUtils.testTitle("Enter Number of leaves to be produced");
	getDriver().findElement(By.id("NoOfLeaves")).clear();
	getDriver().findElement(By.id("NoOfLeaves")).sendKeys(leaves);
	
	TestUtils.testTitle("Confirm that you can see Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//gtibank-chequebook-collection/form/p", "Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Orange Locker')]", "Orange Locker");
	TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Branch')]", "Branch");
	
	
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
	
			getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
			Thread.sleep(500);
    
			//Select Pick up Option
			getDriver().findElement(By.xpath("//gtibank-branchpickup/form/div[2]/ng-select/div")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
			TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");

			//Click on Self Option
			TestUtils.testTitle("Customer selects Self Pick Up Option");
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
				getDriver().findElement(By.xpath("//div/div/div[3]/button[2]")).click();
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
				Thread.sleep(2000);
	
	
				String msg = getDriver().findElement(By.xpath("//h3")).getText();
				String msg2 = getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div/div/div")).getText();
	
				testInfo.get().info(msg + "\n " + msg2 );  
		
	
	//close page
	
				getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div[2]/button")).click();
	
	
			
	
				
	
}

@Test
@Parameters ("testEnv")

public void chequeCollectionBranchThirdPartyPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		
		String token = (String) envs.get("token");
		String leaves = (String) envs.get("leaves");
		String PickUpName = (String) envs.get("PickUpName");
	
	TestUtils.testTitle("Confirm that you can see Account to Debit");
	TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Select Account To Debit:");
	
	//Select Account to debit
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	
	//Enter Number of Leaves
	TestUtils.testTitle("Enter Number of leaves to be produced");
	getDriver().findElement(By.id("NoOfLeaves")).clear();
	getDriver().findElement(By.id("NoOfLeaves")).sendKeys(leaves);
	
	TestUtils.testTitle("Confirm that you can see Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//gtibank-chequebook-collection/form/p", "Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Orange Locker')]", "Orange Locker");
	TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Branch')]", "Branch");
	
	
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
	
			getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
			Thread.sleep(500);
    
			//Select Pick up Option
			getDriver().findElement(By.xpath("//gtibank-branchpickup/form/div[2]/ng-select/div")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
			TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Third Party");
			
			
			//Click on Third Party Option
			 TestUtils.testTitle("Customer selects Third Party Pick Up Option");
			 getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
			Thread.sleep(500);
			
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
	    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[3]")).click();
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
				getDriver().findElement(By.xpath("//div/div/div[3]/button[2]")).click();
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
				Thread.sleep(2000);
	
	
				String msg = getDriver().findElement(By.xpath("//h3")).getText();
				String msg2 = getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div/div/div")).getText();
	
				testInfo.get().info(msg + "\n " + msg2 );  
		
	
	//close page
	
				getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div[2]/button")).click();
	
	
			
	
				
	
}
@Test
@Parameters ("testEnv")
public void chequeCollectionOrangeLockerSelfPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		
		String token = (String) envs.get("token");
		String leaves = (String) envs.get("leaves");
	
	TestUtils.testTitle("Confirm that you can see Account to Debit");
	TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Select Account To Debit:");
	
	//Select Account to debit
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	
	//Enter Number of Leaves
	TestUtils.testTitle("Enter Number of leaves to be produced");
	getDriver().findElement(By.id("NoOfLeaves")).clear();
	getDriver().findElement(By.id("NoOfLeaves")).sendKeys(leaves);
	
	TestUtils.testTitle("Confirm that you can see Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//gtibank-chequebook-collection/form/p", "Select Pickup Options");
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
			TestUtils.scrollToElement("XPATH", "//button[@type='submit']");	 
			getDriver().findElement(By.xpath("//button[@type='submit']")).click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
				TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");

				// Enter Token
				getDriver().findElement(By.id("token")).clear();
				getDriver().findElement(By.id("token")).sendKeys(token);


	
	//Click on submit on Token COnfirmation page
				getDriver().findElement(By.xpath("//div/div/div[3]/button[2]")).click();
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
				Thread.sleep(2000);
	
	
				String msg = getDriver().findElement(By.xpath("//h3")).getText();
				String msg2 = getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div/div/div")).getText();
	
				testInfo.get().info(msg + "\n " + msg2 );  
		
	
	//close page
	
				getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div[2]/button")).click();
	
	
			
	
				
	
}

@Test
@Parameters ("testEnv")

public void chequeCollectionOrangeLockerThirdPartyPickUpTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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


		
		String token = (String) envs.get("token");
		String leaves = (String) envs.get("leaves");
		String PickUpName = (String) envs.get("PickUpName");
	
	TestUtils.testTitle("Confirm that you can see Account to Debit");
	TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Select Account To Debit:");
	
	//Select Account to debit
	getDriver().findElement(By.xpath("//ng-select[@id='account']/div")).click();
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
	
	//Enter Number of Leaves
	TestUtils.testTitle("Enter Number of leaves to be produced");
	getDriver().findElement(By.id("NoOfLeaves")).clear();
	getDriver().findElement(By.id("NoOfLeaves")).sendKeys(leaves);
	
	TestUtils.testTitle("Confirm that you can see Select Pickup Options");
	TestUtils.assertSearchText("XPATH", "//gtibank-chequebook-collection/form/p", "Select Pickup Options");
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
			
			
			//Click on Third Party Option
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
	    	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[3]")).click();
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
				getDriver().findElement(By.xpath("//div/div/div[3]/button[2]")).click();
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
				Thread.sleep(2000);
	
	
				String msg = getDriver().findElement(By.xpath("//h3")).getText();
				String msg2 = getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div/div/div")).getText();
	
				testInfo.get().info(msg + "\n " + msg2 );  
		
	
	//close page
	
				getDriver().findElement(By.xpath("//gtibank-chequebook-collection/div/div/div[2]/button")).click();
	
	
			
	
				
	
}


}
