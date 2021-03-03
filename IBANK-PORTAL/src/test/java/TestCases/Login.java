package TestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.TestBase;

public class Login extends TestBase{

	@Parameters ("testEnv")
	@Test
	public void loginTest(String testEnv) throws Exception {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
//		File path = null;
//		File classpathRoot = new File(System.getProperty("user.dir"));
//		if (testEnv.equalsIgnoreCase("StagingData")) {
//			path = new File(classpathRoot, "stagingData/data.conf.json");
//		} else {
//			path = new File(classpathRoot, "prodData/data.conf.json");
//		}
//		JSONParser parser = new JSONParser();
//		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
//		JSONObject envs = (JSONObject) config.get("Login");
//
//		String user = (String) envs.get("user");
//		String user = (String) envs.get("user");
		if (getDriver().findElement(By.linkText("Get Started with the new Ibank")).isDisplayed()) {
			 getDriver().findElement(By.linkText("Get Started with the new Ibank")).click();
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
			 Thread.sleep(1000);
		}
		
		
		getDriver().findElement(By.id("username")).clear();
	    getDriver().findElement(By.id("username")).sendKeys("205154202");
	    
	    // Enter Password
	    getDriver().findElement(By.id("(//button[@type='button'])[3]")).click();
	    getDriver().findElement(By.id("(//button[@type='button'])[2]")).click();
	    getDriver().findElement(By.id("(//button[@type='button'])[6]")).click();
	    getDriver().findElement(By.id("(//button[@type='button'])[4]")).click();
	    getDriver().findElement(By.id("(//button[@type='button'])[7]")).click();
	    getDriver().findElement(By.id("(//button[@type='button'])[11]")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.id("//button[@type='submit']")).click();
	    
	    
	    
	}
	
}
