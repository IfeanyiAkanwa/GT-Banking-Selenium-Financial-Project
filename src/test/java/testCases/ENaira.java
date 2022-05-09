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

public class ENaira extends TestBase {

	@Test
	public void navigateToENairaTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	

	
	TestUtils.testTitle("Navigate to Dashboard");
	
	//Click on Transfers
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[3]/li/a")));
	getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
	Thread.sleep(500);
	
	TestUtils.testTitle("Navigate To E-Naira");
	
	//CLick on E-Naira
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")));
	getDriver().findElement(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")).click();
	Thread.sleep(500);
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'E-Naira Transfers')]")));
	TestUtils.assertSearchText("XPATH", "//a[contains(text(),'E-Naira Transfers')]", "E-Naira Transfers");
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money through E-Naira");
	Thread.sleep(500);		

}

@Test
public void transferValidationTest() throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	
	//Confirm that the Menu list is displayed and takes the user back to the transfer page when clicked.
	
	
	getDriver().findElement(By.xpath("//div/div/div[2]/a/span/span")).click();
	TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Transfer money between your accounts or other accounts.");
	Thread.sleep(500);
			
			
			//Click on E-Naira
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")));
			getDriver().findElement(By.xpath("//mat-grid-tile[6]/figure/mat-card/p")).click();
			Thread.sleep(500);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'E-Naira Transfers')]")));
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'E-Naira Transfers')]", "E-Naira Transfers");
			TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Send money through E-Naira");
			Thread.sleep(500);	
			
			
			TestUtils.assertSearchText("XPATH", "//gtibank-e-naira/div/div/div/div/form/p", "Transfer to and from your eNaira Account.");
			Thread.sleep(500);	
			
			
			
			Transfers.frequentTransfers();
			TestUtils.raterTest("E-Naira Transfer");
}


@Test
@Parameters ("testEnv")

public void ENairaTest(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
	JSONObject envs = (JSONObject) config.get("ENaira");

	String UserName = (String) envs.get("UserName");
	String Password = (String) envs.get("Password");
	String amount = (String) envs.get("amount");
	String Remark = (String) envs.get("Remark");		
	String token = (String) envs.get("token");
	
	
	TestUtils.testTitle("Select ENaira Account Type and confirm that Individual and Merchant are Displayed");
	getDriver().findElement(By.xpath("//ng-select/div")).click();
	Thread.sleep(500);
	
	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Individual");
	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Merchant");
	
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
	Thread.sleep(500);
	
	

	

	
	
	TestUtils.testTitle("Select Transfer Type and confirm that Transfer and Withdraw are Displayed");
	
	/*
	 * TestUtils.assertSearchText("XPATH",
	 * "//div[contains(text(), 'Select Transfer Type')]", "Select Transfer Type");
	 * Thread.sleep(500);
	 */
	getDriver().findElement(By.xpath("//div/form/div[2]/div/div/div[2]/ng-select/div")).click();
	Thread.sleep(500);
	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[1]", "Transfer To My eNaira");
	TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Withdraw from my eNaira");
	getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
	
	Thread.sleep(500);
	
	
	//Enter Amount
	
	getDriver().findElement(By.id("amount")).clear();
	getDriver().findElement(By.id("amount")).sendKeys(amount);
	
	//Enter UserName 
	getDriver().findElement(By.id("userName")).clear();
	getDriver().findElement(By.id("userName")).sendKeys(UserName);
	
	//Enter Password
		getDriver().findElement(By.id("userName")).clear();
		getDriver().findElement(By.id("userName")).sendKeys(Password);
		
		//Enter Remark 
		getDriver().findElement(By.id("userName")).clear();
		getDriver().findElement(By.id("userName")).sendKeys(Remark);
		
		
		// Click on Continue
		getDriver().findElement(By.xpath("//button[@id='submitBtn']/span/span")).click();
		Thread.sleep(500);
}



}
