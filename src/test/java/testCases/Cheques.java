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

public class Cheques extends SelfService{
	
	@Test
	public void navigateToChequesTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// Click on Self Service
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[9]/li/a")));
		getDriver().findElement(By.xpath("//ul[9]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Cheques')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Cheques')]", "Cheques");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Confirm Cheque or Stop Cheque");
		Thread.sleep(500);			
	}
	
	
	@Test
	public static void chequeValidationsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		TestUtils.testTitle("To confirm that when user clicks on 'Menu List', user is directed back to the main Transfer Module");
		
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div[2]/a/span/span", "Menu list");
		getDriver().findElement(By.xpath("//gtibank-pageheader/div/div/div[2]/a/span/span")).click();
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Self Service')]", "Self Service");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your profile and accounts settings here");
		Thread.sleep(500);
		
		
		//Click on CHeques again
		getDriver().findElement(By.xpath("//mat-grid-tile[3]/figure/mat-card")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Cheques')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Cheques')]", "Cheques");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Confirm Cheque or Stop Cheque");
		Thread.sleep(500);	
		
		
		TestUtils.testTitle("Confirm that SUbmodules are displayed ");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Request Cheque')]", "Request Cheque");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Confirm Cheque')]", "Confirm Cheque");
		TestUtils.assertSearchText("XPATH", "//div/div[contains(text(), 'Stop Cheque')]", "Stop Cheque");		
	}
	
	
	@Test
	@Parameters ("testEnv")
	public void requestChequeSelfTest(String testEnv
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
		JSONObject envs = (JSONObject) config.get("SelfPickUp");


		
			String token = (String) envs.get("token");
		
		TestUtils.testTitle("Confirm that you can see account to debit ");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Account to Debit:");
		
		
		//Select account to debit
		
		getDriver().findElement(By.xpath("//input[@id='account']")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[2]/label", "Select Cheque Book Type");
		
		
		// To confirm that Select Cheque book type displays lists of Cheques
				testInfo.get().info("<b> Select Cheque book Type </b>");
				TestUtils.testTitle("To confirm that Select Cheque book type displays lists of Cheques");
				TestUtils.clickElement("XPATH", "//gtibank-request-cheques/form/div[2]/ng-select/div");
				Thread.sleep(500);
							
							
				getDriver().findElement(By.xpath("//gtibank-request-cheques/form/div[2]/ng-select/div")).click();
				Thread.sleep(500);
				
				
				if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-request-cheques/form/div[2]/ng-select/div")) != null)
				{
					testInfo.get().info("Cheque book type are displayed");
				}
		else 
				{
					testInfo.get().error("Cheque book type are not displayed");
				}
				
				
				//Select cheque book type 
				
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
				Thread.sleep(500);
				
				
				TestUtils.testTitle("Confirm that number of ChequeBooks and increment and decrement buttons are displayed");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-request-cheques/form/label")));
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/label", "Number of chequeBooks");
				
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[3]", "");
				
				getDriver().findElement(By.xpath("//gtibank-request-cheques/form/div[3]/button[2]")).click();
				Thread.sleep(500);
				
				
				
				// To confirm that Select Pick Up Branches displays lists of Branches
				testInfo.get().info("<b> Select PickUp Branch </b>");
				TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
				TestUtils.clickElement("ID", "pickUpBranch");
				Thread.sleep(500);
							
							
				getDriver().findElement(By.id("pickUpBranch")).click();
				Thread.sleep(500);
				
				
				if(ExpectedConditions.visibilityOfElementLocated(By.xpath("pickUpBranch")) != null)
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
				getDriver().findElement(By.id("pickUpMethod")).click();
				Thread.sleep(500);
				TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
				TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Proxy");
				
				//Click on Self Option
				TestUtils.testTitle("Customer selects Self Pick Up Option");
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
				Thread.sleep(500);
				
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[6]/label", "Total Amount to be Charged");
				
				String Charge = getDriver().findElement(By.id("amount")).getText();
				TestUtils.assertSearchText("ID", "amount", Charge);
				
				
				//Click on submit
				TestUtils.scrollToElement("XPATH", "//button[@type='submit']");	 
				getDriver().findElement(By.xpath("//button[@type='submit']")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
				TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
				
				// Enter Token
				getDriver().findElement(By.id("token")).clear();
				getDriver().findElement(By.id("token")).sendKeys(token);
			
				//Click on submit on Token COnfirmation page
				getDriver().findElement(By.xpath("//div/div[3]/button[2]")).click();
				    	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ibank-notifications/div/div/div[1]")));
				Thread.sleep(2000);
				    	
				    	
				String msg = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div[1]")).getText();
				
				    	
				testInfo.get().info(msg + "\n " );  
				    		
						
						//close page
						
				getDriver().findElement(By.xpath("//div/div/div[2]/button")).click();
						
						
	}
	@Test
	@Parameters ("testEnv")
	public void requestChequeProxyTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
			String token = (String) envs.get("token");
			
			
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cheques')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cheques')]");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Cheques')]");
		
		TestUtils.testTitle("Confirm that you can see account to debit ");
		TestUtils.assertSearchText("XPATH", "//gtibank-accounts-typeahead/div/label", "Account to Debit:");
		
		
		//Select account to debit
		
		getDriver().findElement(By.xpath("//input[@id='account']")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div/p[1]")).click();
		Thread.sleep(500);
		
		TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[2]/label", "Select Cheque Book Type");
		
		
		// To confirm that Select Cheque book type displays lists of Cheques
				testInfo.get().info("<b> Select Cheque book Type </b>");
				TestUtils.testTitle("To confirm that Select Cheque book type displays lists of Cheques");
				TestUtils.clickElement("XPATH", "//gtibank-request-cheques/form/div[2]/ng-select/div");
				Thread.sleep(500);
							
							
				getDriver().findElement(By.xpath("//gtibank-request-cheques/form/div[2]/ng-select/div")).click();
				Thread.sleep(500);
				
				
				if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-request-cheques/form/div[2]/ng-select/div")) != null)
				{
					testInfo.get().info("Cheque book type are displayed");
				}
		else 
				{
					testInfo.get().error("Cheque book type are not displayed");
				}
				
				
				//Select cheque book type 
				
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[5]")).click();
				Thread.sleep(500);
				
				
				TestUtils.testTitle("Confirm that number of ChequeBooks and increment and decrement buttons are displayed");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-request-cheques/form/label")));
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/label", "Number of chequeBooks");
				
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[3]", "");
				
				getDriver().findElement(By.xpath("//gtibank-request-cheques/form/div[3]/button[2]")).click();
				Thread.sleep(500);
				
				
				
				// To confirm that Select Pick Up Branches displays lists of Branches
				testInfo.get().info("<b> Select PickUp Branch </b>");
				TestUtils.testTitle("To confirm that Select Pick Up Branches displays lists of Branches");
				TestUtils.clickElement("ID", "pickUpBranch");
				Thread.sleep(500);
							
							
				getDriver().findElement(By.id("pickUpBranch")).click();
				Thread.sleep(500);
				
				
				if(ExpectedConditions.visibilityOfElementLocated(By.xpath("pickUpBranch")) != null)
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
				getDriver().findElement(By.id("pickUpMethod")).click();
				Thread.sleep(500);
				TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Self");
				TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Proxy");
				
				//Click on Proxy Option
				TestUtils.testTitle("Customer selects Proxy Pick Up Option");
				getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
				Thread.sleep(500);
				
				//Enter Third Party Pick Up Name
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtibank-request-cheques/form/div[6]/label")));
		        getDriver().findElement(By.id("proxyName")).click();
		        getDriver().findElement(By.id("proxyName")).clear();
				getDriver().findElement(By.id("proxyName")).sendKeys(PickUpName);
				
				TestUtils.assertSearchText("XPATH", "//gtibank-request-cheques/form/div[6]/label", "Total Amount to be Charged");
				
				String Charge = getDriver().findElement(By.id("amount")).getText();
				TestUtils.assertSearchText("ID", "amount", Charge);
				
				
				//Click on submit
				TestUtils.scrollToElement("XPATH", "//button[@type='submit']");	 
				getDriver().findElement(By.xpath("//button[@type='submit']")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/h3")));
				TestUtils.assertSearchText("XPATH", "//div/div/h3", "Token Confirmation");
				
				// Enter Token
				getDriver().findElement(By.id("token")).clear();
				getDriver().findElement(By.id("token")).sendKeys(token);
			
				//Click on submit on Token COnfirmation page
				getDriver().findElement(By.xpath("//div/div[3]/button[2]")).click();
				    	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ibank-notifications/div/div/div[1]")));
				Thread.sleep(2000);
				    	
				    	
				String msg = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div[1]")).getText();
				
				    	
				testInfo.get().info(msg + "\n " );  
				    		
						
						//close page
						
				getDriver().findElement(By.xpath("//div/div/div[2]/button")).click();
						
						
	}
	
	
	

}
