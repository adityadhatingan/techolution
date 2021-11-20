package saucedemo;

import org.openqa.selenium.By;

/** This interface file contains constants to test login feature of saucedemo. */
public interface LoginTestConstants {
  String SAUCEDEMO_WEBADDRESS = "https://www.saucedemo.com/";
  By USER_NAME = By.xpath("//input[@id='user-name']");
  By PASSWORD = By.xpath("//input[@id='password']");
  By SUBMIT_BUTTON = By.xpath("//input[@id='login-button']");
  String ASSERT_MESSAGE_LOGIN_POSITIVE = "Login Positive Test Failed";
  String ASSERT_MESSAGE_LOGIN_NEGATIVE = "Login Negative Test Failed";
}
