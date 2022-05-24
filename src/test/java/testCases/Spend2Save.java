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

public class Spend2Save extends TestBase {
	@Test
public void navigateToSpend2Save() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Loans and Savings");
		
		//Click on Loans And Savings 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[8]/li/a/span[2]")));
		getDriver().findElement(By.xpath("//ul[8]/li/a/span[2]")).click();
		Thread.sleep(500);
		
		
		//CLick on Spend2Save
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Spend2Save')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Spend2Save')]", "Spend2Save");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Save cash while you spend. Create a Spend2Save account today");
		Thread.sleep(500);	
		
		
	}
@Test
public void loansAndSavingsValidationTest() throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	
	//Confirm that the Menu list is displayed and takes the user back to the Loans and Savings Page when clicked.
			
	TestUtils.testTitle("To confirm that when user clicks on 'Menu List', user is directed back to the main Loans and Savings Module");
	TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Spend2Save')]");
	Thread.sleep(500);
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div[2]/a/span/span", "Menu list");
	getDriver().findElement(By.xpath("//gtibank-pageheader/div/div/div[2]/a/span/span")).click();
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your loans and savings, apply for new loans, view investment rates");
	Thread.sleep(500);
	//CLick on Max Advance Again
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card/p")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Spend2Save')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Spend2Save')]", "Spend2Save");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Save cash while you spend. Create a Spend2Save account today");
		Thread.sleep(500);	
		}

@Parameters ("testEnv")
@Test
public void spend2SaveTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	
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

	//String employerName = (String) envs.get("employerName");		
	String acct = (String) envs.get("salary");
	//The account number field does not search, we did not insert account number 
	
	TestUtils.assertSearchText("XPATH", "//mat-form-field[@id='collectionsSearchInput']/div/div/div[4]", "Enter account number to search...");
	String acctType = getDriver().findElement(By.xpath("//gtibank-view-spend2save/div/form/div/div/div/div/p[1]")).getText();
	String acctNum = getDriver().findElement(By.xpath("//gtibank-view-spend2save/div/form/div/div/div/div/h4")).getText();
	String saveInfo= getDriver().findElement(By.xpath("//gtibank-view-spend2save/div/form/div/div/div/div/p[2]")).getText();
		
	
	testInfo.get().info("The displayed account Type is " + acctType);
	testInfo.get().info("The displayed account number is " + acctNum);
	testInfo.get().info(saveInfo);
	
	
	//Click on the percentage amount to be saved.
	
	getDriver().findElement(By.xpath("//mat-button-toggle[3]")).click();
	Thread.sleep(500);
	
	getDriver().findElement(By.xpath("//gtibank-view-spend2save/div/form/div/div/div/div/div/button")).click();
	Thread.sleep(500);
	
	String displayedmsg = getDriver().findElement(By.xpath("//ibank-notifications/div/div/div/div/h3")).getText();
	String displayedInfo= getDriver().findElement(By.xpath("//ibank-notifications/div/div/div/div/div")).getText();
		
	testInfo.get().info(displayedmsg + "/n" + displayedInfo);
	
	//Click on close
	getDriver().findElement(By.xpath("//ibank-notifications/div/div/div[2]/button")).click();
	Thread.sleep(500);
	
}

}
