package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class StatementCollection extends TestBase{
	
	@Test
	public void navigateToStatementCollectionTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		IRequire.navigateToIRequireTest();
		
		// Click on statement Collection
		TestUtils.testTitle("Click on statement Collection");
		getDriver().findElement(By.xpath("//div[2]/div/div/div[2]/div")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[2]/div", "Statement Collection");
		Thread.sleep(500);
	}

}
