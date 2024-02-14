package testCases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class AccountTypes extends TestBase{
	
	
	@Test
	public void validateAccountTypeVisibility() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Account Types");
		
		TestUtils.assertSearchText("XPATH", "//h2[contains(text(),'Get Started')]", "Get Started");
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Select account type')]", "Select account type");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Personal')]", "Personal");
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Business')]", "Business");
		
		
	}
	
	public void otpInput() throws InterruptedException, FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("stagingData/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Otp");
		
		String otp = envs.getString("otp");
				
				
		getDriver().findElement(By.xpath("//span[contains(text(),'Continue to New Interface')]")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//h2[contains(text(),'Get Started')]", "Get Started");
		getDriver().findElement(By.xpath("//img[@alt='Personal icon']")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//h1[contains(text(),'What do you want to do?')]", "What do you want to do?");
		getDriver().findElement(By.xpath("//span[contains(text(),'Open a New Account')]")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("//span[contains(text(),'Wallet')]")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("phone")).sendKeys(otp);

	}
	
	@Test
	public void navigateToPersonalAccountType() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Personal Account Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Personal icon']")));
		getDriver().findElement(By.xpath("//img[@alt='Personal icon']")).click();
		Thread.sleep(500);
		String URL = getDriver().getCurrentUrl();
		Assert.assertEquals(URL, "https://aowe-test-n9wqh.ondigitalocean.app/personal");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//button/span")).click();

		
	}
	
	@Test
	public void navigateToBusinessAccountType() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Business Account Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Business icon']")));
		getDriver().findElement(By.xpath("//img[@alt='Business icon']")).click();
		Thread.sleep(500);
		String URL = getDriver().getCurrentUrl();
		Assert.assertEquals(URL, "https://aowe-test-n9wqh.ondigitalocean.app/business");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//button/span")).click();

	}
	

}
