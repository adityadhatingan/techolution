package saucedemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.IntStream;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.asserts.SoftAssert;

/** This class contains methods to test the login feature of saucedemo. */
public class LoginTest implements LoginTestConstants {
  private final WebDriver driver;
  private final WebDriverWait wait;
  private final SoftAssert softAssertion;

  /** This is the constructor of the class loginTest for object initialization. */
  public LoginTest(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 20);
    this.softAssertion = new SoftAssert();
  }

  /**
   * This method test all the login cases.
   * @throws IOException
   */
  public void testLogin() throws IOException {
    this.driver.get(SAUCEDEMO_WEBADDRESS);
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(USER_NAME));
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
    Properties prop = new Properties();
    FileInputStream proppath = new FileInputStream("src\\config\\config.properties");
    prop.load(proppath);
    FileInputStream datasheet = new FileInputStream(prop.getProperty("excelPath"));
    XSSFWorkbook workbook = new XSSFWorkbook(datasheet);
    XSSFSheet sheet = workbook.getSheetAt(0);
    Row usernameRow = sheet.getRow(0);
    Row passwordRow = sheet.getRow(1);
    Row expectedResult = sheet.getRow(2);
    String LOGIN_PAGE_URL = this.driver.getCurrentUrl();
    IntStream.rangeClosed(1, 3)
        .forEach(
            i -> {
              try {
                this.driver
                    .findElement(USER_NAME)
                    .sendKeys(usernameRow.getCell(i).getStringCellValue());
                this.driver
                    .findElement(PASSWORD)
                    .sendKeys(passwordRow.getCell(i).getStringCellValue());
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
              this.driver.findElement(SUBMIT_BUTTON).click();
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              String AFTER_LOGIN_PAGE_URL = this.driver.getCurrentUrl();
              if (expectedResult.getCell(i).getStringCellValue().contentEquals("PASS")) {
                this.softAssertion.assertNotEquals(
                    LOGIN_PAGE_URL, AFTER_LOGIN_PAGE_URL, ASSERT_MESSAGE_LOGIN_POSITIVE);
              } else {
                this.softAssertion.assertEquals(
                    LOGIN_PAGE_URL, AFTER_LOGIN_PAGE_URL, ASSERT_MESSAGE_LOGIN_NEGATIVE);
                try {
                  this.driver.findElement(USER_NAME).clear();
                  this.driver.findElement(PASSWORD).clear();
                } catch (NoSuchElementException e) {
                  e.printStackTrace();
                }
              }
            });
    this.softAssertion.assertAll();
  }
}
