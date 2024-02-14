package testCases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class OtpTest extends TestBase{
	
	@Test
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

}
