package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Assertion extends TestBase {

	
	public static void logoDisplayTest() throws InterruptedException {

		TestUtils.testTitle("To verify that GTBank Logo is displayed");
		Thread.sleep(500);
		WebElement img = getDriver().findElement(By.xpath("//img[@alt='GTBank Logo']"));

		String deviceImg = img.getAttribute("src");
		String validImage = "Logo_gtbank.png";
		String emptyImage = "image";

		if (deviceImg.endsWith(validImage)) {
			testInfo.get().info("Logo is displayed");
		} else if (deviceImg.endsWith(emptyImage)) {
			testInfo.get().error("Logo is not displayed");
		} 
	}
}
