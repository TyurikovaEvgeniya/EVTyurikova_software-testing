package ru.stqa.pft.addressbook;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class UserCreationTests {
  private WebDriver wd;
  private JavascriptExecutor js;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\kirra\\Downloads\\chromedriver_win32_100\\chromedriver.exe");
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    js = (JavascriptExecutor) wd;
    wd.get("http://localhost/addressbook/");
    login("admin", "secret");
  }

  private void login(String userName, String password) {
    wd.findElement(By.name("user")).click();
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(userName);
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testUserCreation() throws Exception {

    addNewUser();
    fillUserData(
            new userData("Евгения", "Вячеславовна", "Тюрикова", "+79169928151", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988"));
    submitUserCreation();
    gotoHomePage();
  }

  private void gotoHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  private void submitUserCreation() {
    wd.findElement(By.xpath("//input[21]")).click();
  }

  private void fillUserData(userData userData) {
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(userData.getFirstname());
    wd.findElement(By.name("middlename")).clear();
    wd.findElement(By.name("middlename")).sendKeys(userData.getMiddlename());
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(userData.getLastname());
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(userData.getMobilePhone());
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(userData.getEmail());
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(userData.getBday());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(userData.getBmonth());
    wd.findElement(By.name("byear")).clear();
    wd.findElement(By.name("byear")).sendKeys(userData.getByear());
  }

  private void addNewUser() {
    wd.findElement(By.linkText("add new")).click();
  }

  private void logout() {
    wd.findElement(By.linkText("Logout")).click();
    wd.get("http://localhost/addressbook/");
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    logout();
    wd.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
