package saucedemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/** This class is the main runner of the login test for saucedemo. */
public class LoginTestRunner {

  public static void main(String[] args) throws IOException {
    Properties prop = new Properties();
    FileInputStream proppath = new FileInputStream("src\\config\\config.properties");
    prop.load(proppath);
    System.setProperty("webdriver.chrome.driver", prop.getProperty("chromePath"));
    WebDriver driver = new ChromeDriver();
    driver.manage().window().maximize();
    LoginTest saucedemoLogin = new LoginTest(driver);
    saucedemoLogin.testLogin();
  }
}
