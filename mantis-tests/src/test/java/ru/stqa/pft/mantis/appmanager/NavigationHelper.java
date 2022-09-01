package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd, ApplicationManager app) {
    super(wd,app);
  }

  WebDriverWait wait  = new WebDriverWait(wd, Duration.ofSeconds(10));

  public void management() {
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("*//span[contains(text(),'Управление')]")));
    wd.findElement(By.xpath("*//span[contains(text(),'Управление')]")).click();
  }

  public void userManagement() {
    click(By.xpath("*//a[contains(text(),'Управление пользователями')]"));
  }

  public void drillDownToUserProfile(String userName) {
    wd.findElement(By.xpath(String.format("*//a[contains(text(),'%s')]", userName))).click();
  }

  public void resetPassword() {
    wd.findElement(By.cssSelector("input[value='Сбросить пароль']")).click();
  }


  public void confirm(String confirmationLink) {
    wd.get(confirmationLink);
  }

}
