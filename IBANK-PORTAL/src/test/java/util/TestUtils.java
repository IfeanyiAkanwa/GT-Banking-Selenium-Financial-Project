package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import enums.TargetTypeEnum;

public class TestUtils extends TestBase {

	/**
	 * @param driver
	 * @param screenshotName
	 * @return
	 * @throws IOException
	 * @description to take a screenshot
	 */
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "/TestsScreenshots/" + screenshotName + dateName + ".png";

		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	/**
	 * @param type
	 * @param element
	 * @throws InterruptedException
	 * @description to scroll down to a particular element on the page.
	 */
	public static void scrollToElement(String type, String element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement scrollDown = null;

		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
		switch (targetTypeEnum) {
		case ID:
			scrollDown = getDriver().findElement(By.id(element));
			break;
		case NAME:
			scrollDown = getDriver().findElement(By.name(element));
			break;
		case CSSSELECTOR:
			scrollDown = getDriver().findElement(By.cssSelector(element));
			break;

		case XPATH:
			scrollDown = getDriver().findElement(By.xpath(element));
			break;

		default:
			scrollDown = getDriver().findElement(By.id(element));
			break;
		}

		jse.executeScript("arguments[0].scrollIntoView();", scrollDown);
		Thread.sleep(3000);
	}

	/**
	 * @param type
	 * @param element
	 * @param value
	 * @throws InterruptedException
	 * @description to check if the expected text is present in the page.
	 */
	public static void assertSearchText(String type, String element, String value) throws InterruptedException {

		StringBuffer verificationErrors = new StringBuffer();
		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
		String ttype = null;

		switch (targetTypeEnum) {
		case ID:
			ttype = getDriver().findElement(By.id(element)).getText();
			break;
		case NAME:
			ttype = getDriver().findElement(By.name(element)).getText();
			break;
		case CSSSELECTOR:
			ttype = getDriver().findElement(By.cssSelector(element)).getText();
			break;

		case XPATH:
			ttype = getDriver().findElement(By.xpath(element)).getText();
			break;

		default:
			ttype = getDriver().findElement(By.id(element)).getText();
			break;
		}

		try {
			Assert.assertEquals(ttype, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
	}

	/**
	 * @return number
	 * @description to generate a 11 digit number.
	 */
	public static String generatePhoneNumber() {

		long y = (long) (Math.random() * 100000 + 0333330000L);
		String Surfix = "080";
		String num = Long.toString(y);
		String number = Surfix + num;
		return number;

	}

	public static String generateSimSerial() {

		long y = (long) (Math.random() * 10000000 + 0333330000333333333333L);
		String surfix = "F";
		String num = Long.toString(y);
		String number = num + surfix;
		return number;

	}

	public static String generatePhoneNumber2() {

		long y = (long) (Math.random() * 100000 + 0334440000L);
		String Surfix = "080";
		String num = Long.toString(y);
		String number = Surfix + num;
		return number;

	}

	/**
	 * @return number
	 * @description to generate a 5 digit number
	 */
	public static String generateSerialNumber() {

		long y = (long) (Math.random() * 10000 + 10000L);
		String num = Long.toString(y);
		return num;

	}

	/**
	 * @return number
	 * @description to generate a 6 digit number
	 */
	public static String generateSixSerialNumber() {

		long y = (long) (Math.random() * 10000 + 100000L);
		String num = Long.toString(y);
		return num;
	}
	
	/**
	 * @param value
	 * @return string value.
	 * @throws InterruptedException
	 * @description to convert string value to int value for calculations
	 */
	public static Integer convertToInt(String value) throws InterruptedException {
		Integer result = null;
		String convertedString = value.replaceAll("[^0-9]", "");
		if (validateParams(convertedString)) {
			try {
				return result = Integer.parseInt(convertedString);
			} catch (NumberFormatException e) {
				testInfo.get().error("convertToInt  Error converting to integer ");
				testInfo.get().error(e);
			}
		}
		return result;

	}

	public static Long convertToLong(String value) {
		Long result = null;
		String convertedString = value.replaceAll("[^0-9]", "");
		if (validateParams(convertedString)) {
			try {
				return result = Long.parseLong(convertedString);
			} catch (NumberFormatException e) {
				testInfo.get().error("ConvertToLong  Error converting to long");
				testInfo.get().error(e);
			}
		}
		return result;
	}

	public static boolean validateParams(Object... params) {

		for (Object param : params) {
			if (param == null) {
				return false;
			}

			if (param instanceof String && ((String) param).isEmpty()) {
				return false;
			}

			if (param instanceof Collection<?> && ((Collection<?>) param).isEmpty()) {
				return false;
			}

			if (param instanceof Long && ((Long) param).compareTo(0L) == 0) {
				return false;
			}
			if (param instanceof Double && ((Double) param).compareTo(0D) == 0) {
				return false;
			}

			if (param instanceof Integer && ((Integer) param).compareTo(0) == 0) {
				return false;
			}

		}

		return true;
	}

	public static String addScreenshot() {

		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File scrFile = ts.getScreenshotAs(OutputType.FILE);

		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "data:image/png;base64," + encodedBase64;
	}

	public static boolean isAlertPresents() {
		try {
			getDriver().switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	public static boolean isLoaderPresents() {
		try {
			getDriver().findElement(By.className("dataTables_processing")).isDisplayed();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	public static void clickElement(String type, String element) {
		JavascriptExecutor ex = (JavascriptExecutor) getDriver();
		WebElement clickThis = null;
		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
		switch (targetTypeEnum) {
		case ID:
			clickThis = getDriver().findElement(By.id(element));
			break;
		case NAME:
			clickThis = getDriver().findElement(By.name(element));
			break;
		case CSSSELECTOR:
			clickThis = getDriver().findElement(By.cssSelector(element));
			break;
		case XPATH:
			clickThis = getDriver().findElement(By.xpath(element));
			break;
		default:
			clickThis = getDriver().findElement(By.id(element));
		}
		ex.executeScript("arguments[0].click()", clickThis);
	}

	public static Calendar yyyymmddToDate(String dateString) {
		Calendar dateDate = Calendar.getInstance();

		try {
			String[] dateArray = dateString.split("-");
			int year = Integer.valueOf(dateArray[0]);
			int month = Integer.valueOf(dateArray[1]) - 1;
			int day = Integer.valueOf(dateArray[2]);

			dateDate.set(year, month, day);
		} catch (NumberFormatException e) {
			String[] dateArray = dateString.split("/");
			int year = Integer.valueOf(dateArray[0]);
			int month = Integer.valueOf(dateArray[1]) - 1;
			int day = Integer.valueOf(dateArray[2]);

			dateDate.set(year, month, day);
		}

		return dateDate;
	}

	public static Calendar mmddyyyyToDate(String dateString) {
		Calendar dateDate = Calendar.getInstance();

		try {
			String[] dateArray = dateString.split("-");
			int month = Integer.valueOf(dateArray[0]) - 1;
			int day = Integer.valueOf(dateArray[1]);
			int year = Integer.valueOf(dateArray[2]);

			dateDate.set(month, day, year);
		} catch (NumberFormatException e) {
			String[] dateArray = dateString.split("/");
			int month = Integer.valueOf(dateArray[0]) - 1;
			int day = Integer.valueOf(dateArray[1]);
			int year = Integer.valueOf(dateArray[2]);

			dateDate.set(month, day, year);
		}

		return dateDate;
	}

	public static void checkDateBoundary(String start, String end, String verify) {
		Calendar startDate = mmddyyyyToDate(start);
		Calendar endDate = mmddyyyyToDate(end);
		Calendar verifyDate = yyyymmddToDate(verify);

		if (verifyDate.before(startDate) && verifyDate.after(endDate)) {
			testInfo.get().error("Record not within date range");
		} else {
			testInfo.get().info("Record within date range");
		}
	}

	public static void checkDateyYMDBoundary(String start, String end, String verify) {
		Calendar startDate = yyyymmddToDate(start);
		Calendar endDate = yyyymmddToDate(end);
		Calendar verifyDate = yyyymmddToDate(verify);

		if (verifyDate.before(startDate) && verifyDate.after(endDate)) {
			testInfo.get().error("Record not within date range");
		} else {
			testInfo.get().info("Record within date range");
		}
	}

	public static void assertDivAlert(WebElement alert_element, String expected_message) {
		if (alert_element.getText().contains(expected_message)) {
			testInfo.get().info(expected_message + " found");
		} else {
			testInfo.get().error(expected_message + " not found");
			testInfo.get().error("Expected " + expected_message + " but found " + alert_element.getText());
		}
	}

	public static String CheckBrowser() {
		// Get Browser name and version.
		Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();

		// return browser name and version.
		String os = browserName + " " + browserVersion;

		return os;
	}

	public static ExtentTest isElementPresent2(String elementType, String locator, String value) {

		WebElement elementPresent = null;

		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(elementType);
		switch (targetTypeEnum) {
		case ID:
			try {
				elementPresent = getDriver().findElement(By.id(locator));
			} catch (Exception e) {
			}
			break;
		case NAME:
			try {
				elementPresent = getDriver().findElement(By.name(locator));
			} catch (Exception e) {
			}
			break;
		case CSSSELECTOR:
			try {
				elementPresent = getDriver().findElement(By.cssSelector(locator));
			} catch (Exception e) {
			}
			break;
		case XPATH:
			try {
				elementPresent = getDriver().findElement(By.xpath(locator));
			} catch (Exception e) {
			}
			break;
		default:
			try {
				elementPresent = getDriver().findElement(By.id(locator));
			} catch (Exception e) {
			}
		}
		if (elementPresent == null) {
			return testInfo.get().log(Status.INFO, value + " Element is not present");

		} else {
			return testInfo.get().fail(value + " Element is present");
		}
	}

	public static boolean isElementPresent(String elementType, String locator) {

		WebElement elementPresent = null;

		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(elementType);
		switch (targetTypeEnum) {
		case ID:
			try {
				elementPresent = getDriver().findElement(By.id(locator));
			} catch (Exception e) {
			}
			break;
		case NAME:
			try {
				elementPresent = getDriver().findElement(By.name(locator));
			} catch (Exception e) {
			}
			break;
		case CSSSELECTOR:
			try {
				elementPresent = getDriver().findElement(By.cssSelector(locator));
			} catch (Exception e) {
			}
			break;
		case XPATH:
			try {
				elementPresent = getDriver().findElement(By.xpath(locator));
			} catch (Exception e) {
			}
			break;
		default:
			try {
				elementPresent = getDriver().findElement(By.id(locator));
			} catch (Exception e) {
			}
		}
		if (elementPresent != null) {
			return true;
		} else {
			return false;
		}
	}
	
	 /**
	 * Generates unique string of specified length from the English alphabets
	 * @param length
	 * @return
	 */
	public static String uniqueString(int length) {
		Random rand = new Random();
		StringBuilder result = new StringBuilder();
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		char[] alpha2 = alpha.toCharArray();

		for (int i = 1; i <= length; i++) {

			Integer xcv = rand.nextInt(26);
			result.append(alpha2[xcv]);
		}

		return result.toString();
	}
	
	/**
	 * @param type
	 * @param element
	 * @throws InterruptedException
	 * @description to scroll up to a particular element on the page.
	 */
	public static void scrollUntilElementIsVisible(String type, String element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement scrollUP = null;

		TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
		switch (targetTypeEnum) {
		case ID:
			scrollUP = getDriver().findElement(By.id(element));
			break;
		case NAME:
			scrollUP = getDriver().findElement(By.name(element));
			break;
		case CSSSELECTOR:
			scrollUP = getDriver().findElement(By.cssSelector(element));
			break;

		case XPATH:
			scrollUP = getDriver().findElement(By.xpath(element));
			break;

		default:
			scrollUP = getDriver().findElement(By.id(element));
			break;
		}

		jse.executeScript("window.scrollBy(0,-250)", scrollUP);
		Thread.sleep(500);
	}
	
	public static void testTitle(String phrase) {
		String word = "<b>"+phrase+"</b>";
        Markup w = MarkupHelper.createLabel(word, ExtentColor.BLUE);
        testInfo.get().info(w);
	}
	
	/**
	 * @param by
	 * @param fileName
	 * @description to upload images and files locally and remotely
	 */
	public static void uploadFile(By by, String fileName) {
		try {
			WebElement element = getDriver().findElement(by);
			LocalFileDetector detector = new LocalFileDetector();
			String path = new File(System.getProperty("user.dir") + "/files").getAbsolutePath() + "/" + fileName;
			File file = detector.getLocalFile(path);
			((RemoteWebElement) element).setFileDetector(detector);
			element.sendKeys(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("file upload not successful");
		}
	}
	
	public static void assertValidationMessage(List<String> validationMessages, WebElement inputField, String nameField) {
		String validationMessage = inputField.getAttribute("validationMessage");
		if (validationMessages != null && !validationMessages.isEmpty()) {
			boolean valid = false;
			for(String result : validationMessages){
				if (validationMessage.contains(result)) {
					valid = true;
				}
			}
			Assert.assertEquals(true, valid);
			testInfo.get().info("<b>Empty/Invalid " + nameField + " field:</b> " + validationMessage);
		}
	}

	public static String convertDate(String returnedDate) throws ParseException {
		SimpleDateFormat sdf;
		try{
			sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdff.format(sdf.parse(returnedDate));
			testInfo.get().info("Date returned: " +formattedDate);
			return formattedDate;
		}catch (Exception e){
			sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a");
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdff.format(sdf.parse(returnedDate));
			testInfo.get().info("Date returned: " +formattedDate);
			return formattedDate;
		} 
	}
	
	public static String convertDate2(String returnedDate) throws ParseException {
		SimpleDateFormat sdf;
		try {
			sdf = new SimpleDateFormat("MM/dd/yy, hh:mm a");
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdff.format(sdf.parse(returnedDate));
			testInfo.get().info("Date returned: " + formattedDate);
			return formattedDate;
		} catch (Exception e) {
			sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			SimpleDateFormat sdff = new SimpleDateFormat("MMMM d, yyyy");
			String formattedDate = sdff.format(sdf.parse(returnedDate));
			testInfo.get().info("Date returned: " + formattedDate);
			return formattedDate;
		}
	}
	
	public static String getPreviousWeekDateFromCurrentDate() throws ParseException {
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		// substract 7 days
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 7);

		// convert to date
		Date myDate = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		String strDate = dateFormat.format(myDate);
		return convertDate2(strDate);
	}

	public static String getTodaysDate() throws ParseException {
		
		// Get Calender Instance
		Calendar today = Calendar.getInstance();
		
		// Get Today's date
		today.set(Calendar.HOUR_OF_DAY, 0);
		Date myDate = today.getTime();
		
		// Convert to String 
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		String endDate = dateFormat.format(myDate);
		return convertDate2(endDate);
	}
	
	/**
	 * @param element
	 * @param text
	 * @throws InterruptedException
	 * @description Angular version < 2 apps require this sleep due to a bug
	 */
	public static void setText(By element, String text) throws InterruptedException {
	    Thread.sleep(500);  
	    getDriver().findElement(element).clear();
	    getDriver().findElement(element).sendKeys(text);
	}
	
	public static void getStartedPage() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		try {
			if (getDriver().findElement(By.linkText("Get Started with the new Ibank")).isDisplayed()) {
				getDriver().findElement(By.linkText("Get Started with the new Ibank")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
				Thread.sleep(1000);
			} 
		} catch (Exception e) {
			if (getDriver().findElement(By.id("username")).isDisplayed()) {
				getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
				Thread.sleep(1000);
			}
		}

	}
	
	/**
	 * @param By element - locator of the element
	 * @param title - Title of the page
	 * @description Switches to a new tab from original tab, closes new tab and goes back to the original one
	 */
	public static void switchToNewTab(By element, String title) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		String oldTab = getDriver().getWindowHandle();
		getDriver().findElement(element).click();
	    ArrayList<String> newTab = new ArrayList<String>(getDriver().getWindowHandles());
	    newTab.remove(oldTab);
	    
	    // change focus to new tab
	    getDriver().switchTo().window(newTab.get(0));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong")));
	    Assert.assertEquals(getDriver().getTitle(), title);
	    testInfo.get().info(getDriver().getTitle());
	    
	    // Close new tab
	    getDriver().close();
	    
	    // change focus back to old tab
	    getDriver().switchTo().window(oldTab);
	}

	/**
	 * @param module - name of the module
	 * @description Sends feedback for the module
	 */
	public static void raterTest(String module) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Submit Rater feedback for " + module);
		getDriver().findElement(By.xpath("//gtibank-notifications/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(500);
		String name = getDriver().findElement(By.xpath("//h2")).getText();
		TestUtils.assertSearchText("XPATH", "//h2", name);
		
		// Select stars
		getDriver().findElement(By.xpath("//span[8]")).click();
		Thread.sleep(500);
		
		// Enter Comment
		getDriver().findElement(By.id("Remark")).clear();
		getDriver().findElement(By.id("Remark")).sendKeys("Excellent");
		
		// Submit button
		try {
			getDriver().findElement(By.xpath("//div[3]/div/button/span")).click();
		} catch (Exception e) {
			getDriver().findElement(By.xpath("//form/div[3]/div/button/span")).click();
		}
		
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Feedback Sent Successfully')]", "Feedback Sent Successfully");
		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "We have recieved your feedback. Thank you.");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		Thread.sleep(500);
		
		
	}
	
	public static void imageAlertSwitch() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = 	getDriver().switchTo().alert();	
    		String text = alert.getText();
            alert.accept(); 
            if(text.contains("Error, Invalid File Format")) {
            	testInfo.get().log(Status.INFO,  text+ " found");
            Assert.assertTrue(text.contains("Error, Invalid File Format"));
            } else if (text.contains("Image updated successfully")) {
            	testInfo.get().log(Status.INFO,  text+ " found");
                Assert.assertTrue(text.contains("Image updated successfully"));
            }
        } catch (Exception e) {
            //exception handling
        }
	}
	
	@Test
	public static void accOfficerValidationTest() throws InterruptedException {
		
		TestUtils.testTitle("To confirm that the Account Officer button auto hides the account officer section when clicked");
		
		// Click on Account officer panel
		getDriver().findElement(By.xpath("//mat-panel-title")).click();
		Thread.sleep(500);
		if (getDriver().findElement(By.xpath("//h3")).isDisplayed()) {
			testInfo.get().error("<b> Account officer section is not hidden </b>");
		} else {
			testInfo.get().info("<b> Account officer section is hidden </b>");
		}
		
		// Click on Account officer panel
		getDriver().findElement(By.xpath("//mat-expansion-panel-header")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Account Officer's name and Address is displayed");
		Assertion.assertAccountOfficersDetails();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Account Officer's profile image is displayed");
		Assertion.accountOfficersProfileImageDisplayTest();
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Secure Email page is displayed when user clicks on the Send Mail button");
		// Click on Send Mail button
		getDriver().findElement(By.xpath("//mat-action-row/a/span/mat-icon")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Secure Email')]", "Secure Email");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Create a request using a secured email channel");
		Thread.sleep(500);
	}
}
