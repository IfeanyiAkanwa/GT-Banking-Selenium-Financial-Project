package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.TestBase;
import util.TestUtils;

public class TransferToOtherBanks extends TestBase{
	
	public void navigateToTransferToOtherBanksTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Transfer to Other Banks");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		getDriver().findElement(By.xpath("")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		TestUtils.assertSearchText("", "", "");
		Thread.sleep(500);
		
	}

}
