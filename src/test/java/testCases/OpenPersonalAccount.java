package testCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class OpenPersonalAccount extends TestBase{
	
	@Test
	public void navigateToPersonalAccountTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Personal Account");
		// Click on Personal Account
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Personal icon']")));
		getDriver().findElement(By.xpath("//img[@alt='Personal icon']")).click();
		Thread.sleep(1000);
		
		Assertion.validateSelectedAccountType();

	}

	
	
}
