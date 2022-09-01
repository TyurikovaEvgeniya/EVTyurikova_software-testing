package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class HelperBase {


  protected WebDriver wd;
  protected ApplicationManager app;

  public HelperBase(WebDriver wd, ApplicationManager app) {
    this.app = app;
    this.wd = wd;
  }

  public HelperBase(WebDriver wd) {
  }


  protected void click(By locator) {

    try {
      if (wd.findElement(locator).isDisplayed()) {
        wd.findElement(locator).click();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void type(By locator, String text) {
    if (text != null) {
      WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));
      wait.until(ExpectedConditions.presenceOfElementLocated(locator));
      wd.findElement(locator).click();
      String existingText = wd.findElement(locator).getAttribute("value");
      if (!text.equals(existingText)) {
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void attach(By locator, File file) {
    if (file != null) {
      wd.findElement(locator).sendKeys(file.getAbsolutePath());
    }
  }

  protected void selectFromDropDownList(String listName, String listItemValue) {
    new Select(wd.findElement(By.name(listName))).selectByVisibleText(listItemValue);
  }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  public boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }


  private static char rndChar() {
    int rnd = (int) (Math.random() * 52); // or use Random or whatever
    char base = (rnd < 26) ? 'A' : 'a';
    return (char) (base + rnd % 26);

  }
}



